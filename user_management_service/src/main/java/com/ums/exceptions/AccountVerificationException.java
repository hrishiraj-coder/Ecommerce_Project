package com.ums.exceptions;

import com.ums.utilites.AccountVerificationType;
import lombok.Getter;

@Getter
public class AccountVerificationException extends RuntimeException{
    private final AccountVerificationType verificationType;

    public AccountVerificationException(String message, AccountVerificationType verificationType) {
        super(message);
        this.verificationType = verificationType;
    }
}
