package com.ums.controller;

import com.ums.dto.UserAccountDto;
import com.ums.entites.UserAccount;
import com.ums.service.UserAccountService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user_account")
public class UserAccountApi {
    private final UserAccountService userAccountService;

    public UserAccountApi(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @RequestMapping(path = "/save_user",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveUser(@RequestBody UserAccountDto userAccountDto){
        long userid;
        userid = userAccountService.saveUser(userAccountDto);

         return ResponseEntity.ok(String.valueOf(userid));


    }
}
