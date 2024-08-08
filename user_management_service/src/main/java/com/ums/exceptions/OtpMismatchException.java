package com.ums.exceptions;

import lombok.Getter;

@Getter
public class OtpMismatchException extends RuntimeException{


    public OtpMismatchException(String message) {
        super(message);

    }
}
