package com.pragma.powerup.domain.spi.feignclients;

import com.pragma.powerup.domain.model.SmsMessageModel;

public interface ITwilioFeignClientPort {

    void sendSmsMessage(SmsMessageModel smsMessageModel);
}
