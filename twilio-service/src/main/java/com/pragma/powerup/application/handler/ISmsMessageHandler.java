package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.SmsMessageRequestDto;

public interface ISmsMessageHandler {

    void sendSmsMessage(SmsMessageRequestDto smsMessageRequestDto);
}
