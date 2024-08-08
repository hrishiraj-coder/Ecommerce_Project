package com.ums.exceptions.handler;

import com.ums.dto.ErrorMessage;
import com.ums.dto.ErrorMessageFactory;
import com.ums.exceptions.AccountVerificationException;
import com.ums.exceptions.UserAccountNotFoundException;
import com.ums.exceptions.UserAlreadyActivatedException;
import com.ums.exceptions.OtpMismatchException;
import com.ums.utilites.ErrorCodes;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
@AllArgsConstructor
@Component
public class ErrorMessageExceptionHandler {

    private final ErrorMessageFactory factory;

    @ExceptionHandler(UserAccountNotFoundException.class)
    public ResponseEntity<ErrorMessage> userAccountNotFoundExceptionHandler(HttpServletRequest request, UserAccountNotFoundException exception){
        log.error(request.getRequestURL().toString(),exception);
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(factory.failure(exception, ErrorCodes.USER_NOT_FOUND));
    }

    @ExceptionHandler(UserAlreadyActivatedException.class)
    public ResponseEntity<ErrorMessage> userAlreadyActivatedException(HttpServletRequest request, UserAlreadyActivatedException exception){
        log.error(request.getRequestURL().toString(),exception);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(factory.failure(exception,ErrorCodes.USER_ALREADY_ACTIVATED));
    }

    @ExceptionHandler(OtpMismatchException.class)
    public ResponseEntity<ErrorMessage> userVerificationCodeMismatch(HttpServletRequest request, OtpMismatchException exception){
        log.error(request.getRequestURL().toString(),exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(factory.failure(exception,ErrorCodes.VERIFICATION_CODE_MISMATCH));
    }

    @ExceptionHandler(AccountVerificationException.class)
    public ResponseEntity<ErrorMessage> userAlreadyVerifiedException(HttpServletRequest request,AccountVerificationException exception){
        log.error(request.getRequestURL().toString(),exception);
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(factory.failure(exception,ErrorCodes.OTP_ALREADY_VERIFIED));
    }

}
