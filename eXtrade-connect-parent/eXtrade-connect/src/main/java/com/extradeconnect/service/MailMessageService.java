package com.extradeconnect.service;

import com.extradeconnect.beans.message.Message;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
@AllArgsConstructor
public class MailMessageService implements MessageService {
    private JavaMailSender javaMailSender;
    @Override
    public void dispatch(Message message) {

    }
}
