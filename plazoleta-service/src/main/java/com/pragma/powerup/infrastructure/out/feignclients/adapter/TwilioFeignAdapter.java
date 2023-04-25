package com.pragma.powerup.infrastructure.out.feignclients.adapter;

import com.pragma.powerup.application.dto.request.SmsMessageRequestDto;
import com.pragma.powerup.domain.model.SmsMessageModel;
import com.pragma.powerup.domain.spi.feignclients.ITwilioFeignClientPort;
import com.pragma.powerup.infrastructure.out.feignclients.TwilioFeignClients;
import com.pragma.powerup.infrastructure.out.feignclients.mapper.ITwilioMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TwilioFeignAdapter implements ITwilioFeignClientPort {

    private final TwilioFeignClients twilioFeignClients;
    private final ITwilioMapper twilioMapper;
    @Override
    public void sendSmsMessage(SmsMessageModel smsMessageModel) {
        SmsMessageRequestDto smsMessageRequestDto = twilioMapper.toSmsRequest(smsMessageModel);
        twilioFeignClients.sendSmsMessage(smsMessageRequestDto);
    }
}
