package com.extradeconnect.beans.notification;

import com.extradeconnect.beans.Attachment;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(builderMethodName = "of")
public class MailNotification extends Notification{
    private String subject;
    private List<Attachment> attachments;


}
