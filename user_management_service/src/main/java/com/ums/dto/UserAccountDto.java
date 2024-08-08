package com.ums.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder(builderMethodName = "of")
public class UserAccountDto {
    private Long userAccountId;
    private String firstName;
    private String lastName;
    private String mobileNo;
    private String emailAddress;
    private String password;
    private LocalDate dob;
    private String gender;
    private String roleCode;
    private String status;
}
