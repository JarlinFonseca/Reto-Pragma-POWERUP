package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.RolResponseDto;
import com.pragma.powerup.domain.model.Rol;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRolResponseMapper {

    RolResponseDto toResponse(Rol rol);

    List<RolResponseDto> toResposeList(List<Rol> roleList);
}
