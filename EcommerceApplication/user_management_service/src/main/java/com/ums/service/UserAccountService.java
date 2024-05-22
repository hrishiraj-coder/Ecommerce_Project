package com.ums.service;

import com.ums.dto.UserAccountDto;
import com.ums.entites.UserAccount;

public interface UserAccountService {

    long saveUser(UserAccountDto userAccountDto);
    long countByMobile(String mobNo);
    long countByEmail(String email);

}
