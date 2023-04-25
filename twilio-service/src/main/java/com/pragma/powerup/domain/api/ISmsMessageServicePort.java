package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.SmsMessageModel;

public interface ISmsMessageServicePort {
    void sendSmsMessage(SmsMessageModel smsMessageModel);
}
