package com.ums.dto;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ErrorMessageFactory {

    private static final String originator="user-account-service";

    public ErrorMessage failure(Exception exception, String errorCode){
        return ErrorMessage.of()
                .messageId(UUID.randomUUID().toString())
                .errorCode(errorCode)
                .errorMessage(exception.getMessage())
                .messageDateTime(new Date())
                .originator(originator)
                .build();
    }

}
