package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.SmsMessageModel;

public interface ISmsMessageTwilio {

    void sendSmsMessage(SmsMessageModel smsMessageModel);
}
