package com.ums.service;

import com.ums.dto.CustomerVerificationDto;
import com.ums.dto.UserAccountDto;
import com.ums.utilites.AccountVerificationType;

public interface  UserAccountService {

    long saveUser(UserAccountDto userAccountDto);

    long countByMobile(String mobNo);

    long countByEmail(String email);

    CustomerVerificationDto verifyOtpAndUpdateAccountStatus(long id, String verificationCode, AccountVerificationType accountVerificationType);

    CustomerVerificationDto accountVerificationStatus(long userAccountId);

    UserAccountDto getByEmailAddress(String emailAddress);

}
