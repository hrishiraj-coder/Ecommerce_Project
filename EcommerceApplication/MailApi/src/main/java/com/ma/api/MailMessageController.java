package com.ma.api;

import com.ma.data.EmailRequest;
import com.ma.service.MailService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailMessageController {

    private final MailService mailService;

    public MailMessageController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMail(@RequestBody EmailRequest mail) {
        mailService.sendSimpleMail(mail.getTo(), mail.getSubject(), mail.getBody());
        System.out.println("SENT");
        return ResponseEntity.ok("Mail Sent Successfully !");
    }
}
