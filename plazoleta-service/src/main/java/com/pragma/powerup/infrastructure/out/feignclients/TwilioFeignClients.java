package com.pragma.powerup.infrastructure.out.feignclients;

import com.pragma.powerup.application.dto.request.SmsMessageRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "twilio-service", url = "localhost:8083/api/v1/sms/message")
public interface TwilioFeignClients {
    @PostMapping("/")
    ResponseEntity<Void> sendSmsMessage(@Valid @RequestBody SmsMessageRequestDto smsMessageRequestDto);
}
