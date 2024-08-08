package com.ums.service.impl;

import com.ums.dto.CustomerVerificationDto;
import com.ums.dto.UserAccountDto;
import com.ums.entites.Role;
import com.ums.entites.UserAccount;
import com.ums.exceptions.AccountVerificationException;
import com.ums.exceptions.UserAccountNotFoundException;
import com.ums.exceptions.UserAlreadyActivatedException;
import com.ums.exceptions.OtpMismatchException;
import com.ums.feign.MailFeign;
import com.ums.feign.data.EmailRequest;
import com.ums.mapper.UserAccountMapper;
import com.ums.repository.RoleRepository;
import com.ums.repository.UserAccountRepository;
import com.ums.service.UserAccountService;
import com.ums.utilites.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final RoleRepository roleRepository;
    private final MailFeign mailFeign;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserAccountServiceImpl(UserAccountRepository userAccountRepository, UserAccountMapper mapper,
                                  UserAccountRepository userAccountRepository1, RoleRepository roleRepository, MailFeign mailFeign, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userAccountRepository = userAccountRepository1;
        this.roleRepository = roleRepository;
        this.mailFeign = mailFeign;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    @Transactional(readOnly = true)
    public long countByEmail(String email) {
        return userAccountRepository.countByEmailAddress(email);
    }


    @Override
    @Transactional(readOnly = true)
    public long countByMobile(String mobNo) {
        return userAccountRepository.countByMobileNo(mobNo);
    }

    @Override
    @Transactional
    public long saveUser(UserAccountDto userAccountDto) {
        String emailVerificationOtpCode = RandomGenerator.alphaNumericSequenceGenerator(8);
        String mobileNoVerificationOtpCode = RandomGenerator.numericSequenceGenerator(6);
        LocalDateTime time = LocalDateTime.now();
        Role userRole = roleRepository.findByRoleCode(RoleCodeEnum.CUSTOMER.toString());
        log.info("Fetched the User Role Id {} for Role Code {} ", userRole.getRoleId(), RoleCodeEnum.CUSTOMER.toString());

        UserAccount userAccount = new UserAccount();
        userAccount.setFirstName(userAccountDto.getFirstName());
        userAccount.setLastName(userAccountDto.getLastName());
        userAccount.setEmailAddress(userAccountDto.getEmailAddress());
        userAccount.setMobileNo(userAccountDto.getMobileNo());
        userAccount.setPassword(bCryptPasswordEncoder.encode(userAccountDto.getPassword()));
        userAccount.setGender(userAccountDto.getGender());
        userAccount.setDob(userAccountDto.getDob());
        userAccount.setEmailVerificationOtpCode(emailVerificationOtpCode);
        userAccount.setMobileNoVerificationOtpCode(mobileNoVerificationOtpCode);
        userAccount.setEmailVerificationOtpCodeGeneratedDate(time);
        userAccount.setMobileNoVerificationOtpCodeGeneratedDate(time);
        userAccount.setUserRole(userRole);
        userAccount.setRegisteredDate(LocalDate.now());
        userAccount.setEmailVerificationStatus((short) 0);
        userAccount.setMobileNoVerificationStatus((short) 0);
        userAccount.setLastModifiedBy(UserAccountConstant.SYSTEM_USER);
        userAccount.setLastModifiedDate(time);
        userAccount.setStatus(UserAccountStatusEnum.REGISTERED.getName());

        long id = userAccountRepository.save(userAccount).getUserAccountId();
        String mailBodyVerificationLink = "Email OTP: " + emailVerificationOtpCode + "  MobileOTP: " + mobileNoVerificationOtpCode + " Verification Link: " + " http://localhost:8088/customer/" + id + "/" + emailVerificationOtpCode + "/verifyEmail";

        try {
            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setSubject("Account Verification");
            emailRequest.setTo(userAccountDto.getEmailAddress());
            emailRequest.setBody("Your verification otp code for Tradezy is: " + mailBodyVerificationLink);
            mailFeign.sendMail(emailRequest);
        } catch (Exception e) {
            log.error("Email Verification failed for {}", userAccountDto.getEmailAddress(), e);
            e.printStackTrace();
            throw e;
        }

        return id;
    }

    @Override
    @Transactional
    public CustomerVerificationDto verifyOtpAndUpdateAccountStatus(long id, String verificationCode,
                                                                   AccountVerificationType verificationType) {
        UserAccount userAccount = null;
        Optional<UserAccount> optionalUserAccount = userAccountRepository.findById(id);

        if (optionalUserAccount.isEmpty()) {
            throw new UserAccountNotFoundException("User with ID: " + id + " Not Found");
        }

        userAccount = optionalUserAccount.get();

        CustomerVerificationDto dto = CustomerVerificationDto.of().userAccountId(id).emailAddressOtpVerificationStatus(userAccount.getEmailVerificationStatus())
                .mobileNoOtpVerificationStatus(userAccount.getMobileNoVerificationStatus())
                .accountStatus(userAccount.getStatus())
                .build();

        if (dto.getAccountStatus().equals(UserAccountStatusEnum.ACTIVE.toString())) {
            throw new UserAlreadyActivatedException("User ID : " + id + " has already Activated");
        }
        if (verificationType == AccountVerificationType.VERIFY_MOBILE) {
            if (dto.getMobileNoOtpVerificationStatus() == UserAccountConstant.OTP_STATUS_VERIFIED) {
                throw new AccountVerificationException("MobileNo already verified", verificationType);
            }
            if (!userAccount.getMobileNoVerificationOtpCode().equals(verificationCode)) {
                throw new OtpMismatchException("Mobile OTP did not match ");
            }

            dto.setMobileNoOtpVerificationStatus(UserAccountConstant.OTP_STATUS_VERIFIED);
            userAccount.setMobileNoVerificationStatus(UserAccountConstant.OTP_STATUS_VERIFIED);

        } else if (verificationType == AccountVerificationType.VERIFY_EMAIL) {
            if (dto.getEmailAddressOtpVerificationStatus() == UserAccountConstant.OTP_STATUS_VERIFIED) {
                throw new AccountVerificationException("Email already verified", verificationType);
            }
            if (!userAccount.getEmailVerificationOtpCode().equals(verificationCode)) {
                throw new OtpMismatchException("Email OTP did not match ");
            }
            dto.setEmailAddressOtpVerificationStatus(UserAccountConstant.OTP_STATUS_VERIFIED);
            userAccount.setEmailVerificationStatus(UserAccountConstant.OTP_STATUS_VERIFIED);


        }

        if (userAccount.getMobileNoVerificationStatus() == UserAccountConstant.OTP_STATUS_VERIFIED &&
                userAccount.getEmailVerificationStatus() == UserAccountConstant.OTP_STATUS_VERIFIED) {
            userAccount.setStatus(UserAccountStatusEnum.ACTIVE.toString());
            dto.setAccountStatus(UserAccountStatusEnum.ACTIVE.toString());
            userAccount.setActivatedDate(LocalDate.now());
        }
        int records = userAccountRepository.updateUserAccount(id, userAccount.getEmailVerificationStatus(),
                userAccount.getMobileNoVerificationStatus(),
                LocalDateTime.now(), userAccount.getActivatedDate(), userAccount.getStatus());

        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerVerificationDto accountVerificationStatus(long userAccountId) {
        Optional<UserAccount> optionalUserAccount = userAccountRepository.findById(userAccountId);

        if (optionalUserAccount.isEmpty()) {
            throw new UserAccountNotFoundException("User Account with " + userAccountId + " not found");
        }

        UserAccount userAccount = optionalUserAccount.get();

        CustomerVerificationDto customerVerificationDto = CustomerVerificationDto.of()
                .userAccountId(userAccount.getUserAccountId())
                .emailAddressOtpVerificationStatus(userAccount.getEmailVerificationStatus())
                .mobileNoOtpVerificationStatus(userAccount.getMobileNoVerificationStatus())
                .accountStatus(userAccount.getStatus())
                .mobileNo(userAccount.getMobileNo())
                .email(userAccount.getEmailAddress())
                .build();

        return customerVerificationDto;
    }

    @Override
    @Transactional(readOnly = true)
    public UserAccountDto getByEmailAddress(String emailAddress) {
        Optional<UserAccount> optionalUserAccount = userAccountRepository.findByEmailAddress(emailAddress);

        if (optionalUserAccount.isEmpty()) {
            throw new UserAccountNotFoundException("User with " + emailAddress + " does not found");
        }

        UserAccount userAccount = optionalUserAccount.get();
        UserAccountDto dto = UserAccountDto.of()
                .userAccountId(userAccount.getUserAccountId())
                .firstName(userAccount.getFirstName())
                .lastName(userAccount.getLastName())
                .emailAddress(userAccount.getEmailAddress())
                .password(userAccount.getPassword())
                .gender(userAccount.getGender())
                .mobileNo(userAccount.getMobileNo())
                .dob(userAccount.getDob())
                .roleCode(userAccount.getUserRole().getRoleCode())
                .status(userAccount.getStatus())
                .build();

        return dto;


    }


}
