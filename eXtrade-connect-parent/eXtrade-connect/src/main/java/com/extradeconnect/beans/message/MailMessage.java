package com.extradeconnect.beans.message;

import com.extradeconnect.beans.Attachment;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder(builderMethodName = "of")
public class MailMessage extends Message {

   private String subject;
   private List<Attachment> attachments;

}

