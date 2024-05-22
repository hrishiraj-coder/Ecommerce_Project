package com.ums.service.impl;

import com.ums.dto.UserAccountDto;
import com.ums.entites.Role;
import com.ums.entites.UserAccount;
import com.ums.feign.MailFeign;
import com.ums.feign.data.EmailRequest;
import com.ums.mapper.UserAccountMapper;
import com.ums.repository.RoleRepository;
import com.ums.repository.UserAccountRepository;
import com.ums.service.UserAccountService;
import com.ums.utilites.RandomGenerator;
import com.ums.utilites.RoleCodeEnum;
import com.ums.utilites.UserAccountConstant;
import com.ums.utilites.UserAccountStatusEnum;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Slf4j
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final RoleRepository roleRepository;
    private final MailFeign mailFeign;

    public UserAccountServiceImpl(UserAccountRepository userAccountRepository, UserAccountMapper mapper,
                                  UserAccountRepository userAccountRepository1, RoleRepository roleRepository, MailFeign mailFeign) {
        this.userAccountRepository = userAccountRepository1;
        this.roleRepository = roleRepository;
        this.mailFeign = mailFeign;
    }


    @Override
    @Transactional(readOnly = true)
    public long countByEmail(String email) {
        return userAccountRepository.countByEmailAddress(email);
    }

    @Override
    @Transactional(readOnly = true)
    public long countByMobile(String mobNo) {
        return userAccountRepository.countByEmailAddress(mobNo);
    }

    @Override
    public long saveUser(UserAccountDto userAccountDto) {
        String emailVerificationOtpCode = RandomGenerator.alphaNumericSequenceGenerator(8);
        String mobileNoVerificationOtpCode = RandomGenerator.numericSequenceGenerator(6);
        LocalDateTime time = LocalDateTime.now();
        String mailBodyVerificationLink = "<a href='www.google.com'>LINK</a>";
        Role userRole = roleRepository.findByRoleCode(RoleCodeEnum.CUSTOMER.toString());
        log.info("Fetched the User Role Id {} for Role Code {} ", userRole.getRoleId(), RoleCodeEnum.CUSTOMER.toString());

        UserAccount userAccount = new UserAccount();
        userAccount.setFirstName(userAccountDto.getFirstName());
        userAccount.setLastName(userAccountDto.getLastName());
        userAccount.setEmailAddress(userAccountDto.getEmailAddress());
        userAccount.setMobileNo(userAccountDto.getMobileNo());
        userAccount.setPassword(userAccountDto.getPassword());
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

        try {
            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setSubject("Account Verification");
            emailRequest.setTo(userAccountDto.getEmailAddress());
            emailRequest.setBody(mailBodyVerificationLink);
            mailFeign.sendMail(emailRequest);
        } catch (Exception e) {
            log.error("Email Verification failed for {}",userAccountDto.getEmailAddress(),e);
            e.printStackTrace();
            throw e;
        }

        return id;


    }
}
