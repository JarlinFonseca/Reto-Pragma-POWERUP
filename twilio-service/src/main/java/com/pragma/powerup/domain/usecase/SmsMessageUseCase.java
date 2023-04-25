package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.ISmsMessageServicePort;
import com.pragma.powerup.domain.model.SmsMessageModel;
import com.pragma.powerup.domain.spi.ISmsMessageTwilio;

public class SmsMessageUseCase implements ISmsMessageServicePort {

    private final ISmsMessageTwilio smsMessageTwilio;

    public SmsMessageUseCase(ISmsMessageTwilio smsMessageTwilio) {
        this.smsMessageTwilio = smsMessageTwilio;
    }

    @Override
    public void sendSmsMessage(SmsMessageModel smsMessageModel) {
        smsMessageTwilio.sendSmsMessage(smsMessageModel);
    }
}
