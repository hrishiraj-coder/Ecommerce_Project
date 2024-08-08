package com.ums.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder(builderMethodName = "of")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorMessage {

    private String messageId;
    private String errorCode;
    private String errorMessage;
    private String originator;
    @JsonProperty("timestamp")
    private Date messageDateTime;


}
