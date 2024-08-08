package com.ums.exceptions;

public class UserAccountNotFoundException extends RuntimeException{

    public UserAccountNotFoundException(String message){
        super(message);
    }

}
