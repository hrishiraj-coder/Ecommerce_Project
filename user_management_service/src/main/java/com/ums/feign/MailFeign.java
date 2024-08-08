package com.ums.feign;

import com.ums.feign.data.EmailRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "MailApi", url = "http://${mail-api.url}")
public interface MailFeign {

    @PostMapping("/mail/send")
    String sendMail(@RequestBody EmailRequest request);


}
