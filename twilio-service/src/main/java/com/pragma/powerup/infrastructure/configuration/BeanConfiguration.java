package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.ISmsMessageServicePort;
import com.pragma.powerup.domain.spi.ISmsMessageTwilio;
import com.pragma.powerup.domain.usecase.SmsMessageUseCase;
import com.pragma.powerup.infrastructure.out.twilio.adapter.SmsMessageTwilioAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    @Bean
    public ISmsMessageTwilio smsMessageTwilio(){
        return new SmsMessageTwilioAdapter();
    }

    @Bean
    public ISmsMessageServicePort smsMessageServicePort(){
        return new SmsMessageUseCase(smsMessageTwilio());
    }
}