package com.pragma.powerup.infrastructure.out.twilio.adapter;

import com.pragma.powerup.domain.model.SmsMessageModel;
import com.pragma.powerup.domain.spi.ISmsMessageTwilio;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;


@RequiredArgsConstructor
public class SmsMessageTwilioAdapter implements ISmsMessageTwilio {
    @Value("${TWILIO_ACCOUNT_SID}")
    private String ACCOUNT_SID;
    @Value("${TWILIO_AUTH_TOKEN}")
    private String AUTH_TOKEN;
    @Value("${TWILIO_FROM_PHONE_NUMBER}")
    private String FROM_PHONE_NUMBER;


    @Override
    public void sendSmsMessage(SmsMessageModel smsMessageModel) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber(smsMessageModel.getNumero()),
                new PhoneNumber(FROM_PHONE_NUMBER),
                smsMessageModel.getMensaje()).
                        create();

        System.out.println(message.getSid());
    }
}
