package com.pragma.powerup.infrastructure.out.jpa.mapper;

import com.pragma.powerup.domain.model.Rol;
import com.pragma.powerup.infrastructure.out.jpa.entity.RolEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRolEntityMapper {

    RolEntity toEntity(Rol rol);

    Rol toRolModel(RolEntity rolEntity);

    List<Rol> toRolModelList(List<RolEntity> rolEntityList);
}
