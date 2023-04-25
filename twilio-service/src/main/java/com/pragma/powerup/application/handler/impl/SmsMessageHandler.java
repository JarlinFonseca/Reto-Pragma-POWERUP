package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.SmsMessageRequestDto;
import com.pragma.powerup.application.handler.ISmsMessageHandler;
import com.pragma.powerup.application.mapper.ISmsMessageRequestMapper;
import com.pragma.powerup.domain.api.ISmsMessageServicePort;
import com.pragma.powerup.domain.model.SmsMessageModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
public class SmsMessageHandler implements ISmsMessageHandler {

    private final ISmsMessageServicePort smsMessageServicePort;

    private final ISmsMessageRequestMapper smsMessageRequestMapper;

    @Override
    public void sendSmsMessage(SmsMessageRequestDto smsMessageRequestDto) {
        SmsMessageModel smsMessageModel = smsMessageRequestMapper.toSmsMessageModel(smsMessageRequestDto);
        smsMessageServicePort.sendSmsMessage(smsMessageModel);
    }
}
