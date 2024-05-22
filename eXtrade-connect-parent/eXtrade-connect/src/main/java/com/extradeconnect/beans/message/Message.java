package com.extradeconnect.beans.message;

import lombok.Builder;
import lombok.Data;

import java.util.Map;
@Data
@Builder(builderMethodName = "of")
public class Message {
    private String from;
    private String[] to;
    private String text;
}
