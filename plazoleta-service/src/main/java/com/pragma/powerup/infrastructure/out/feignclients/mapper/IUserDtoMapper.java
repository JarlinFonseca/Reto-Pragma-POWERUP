package com.pragma.powerup.infrastructure.out.feignclients.mapper;

import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.infrastructure.out.feignclients.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IUserDtoMapper {

    UserModel toUserModel(UserDto userDto);
}
