package com.ums.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderMethodName = "of")
public class CustomerVerificationDto {
    private Long userAccountId;
    private int mobileNoOtpVerificationStatus;
    private int emailAddressOtpVerificationStatus;
    private String email;
    private String mobileNo;
    private String accountStatus;
}
