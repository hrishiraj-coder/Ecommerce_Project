package com.ums.exceptions;

public class UserAlreadyActivatedException extends RuntimeException {
    public UserAlreadyActivatedException(String message){
          super(message);
    }
}
