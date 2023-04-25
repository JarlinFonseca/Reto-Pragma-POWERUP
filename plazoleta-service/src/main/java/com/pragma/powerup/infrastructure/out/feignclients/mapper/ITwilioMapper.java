package com.pragma.powerup.infrastructure.out.feignclients.mapper;

import com.pragma.powerup.application.dto.request.SmsMessageRequestDto;
import com.pragma.powerup.domain.model.SmsMessageModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface ITwilioMapper {
    SmsMessageRequestDto toSmsRequest(SmsMessageModel smsMessageModel);
}
