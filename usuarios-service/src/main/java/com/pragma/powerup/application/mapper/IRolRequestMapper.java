package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.RolRequestDto;
import com.pragma.powerup.domain.model.Rol;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRolRequestMapper {

    Rol toRol(RolRequestDto rolRequestDto);

}
