package com.pragma.powerup.infrastructure.out.jpa.mapper;

import com.pragma.powerup.domain.model.Usuario;
import com.pragma.powerup.infrastructure.out.jpa.entity.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUsuarioEntityMapper {

    @Mapping(target = "rol.id", source = "rol.id")
    UsuarioEntity toEntity(Usuario usuario);
    @Mapping(target = "rol.id", source = "rol.id")
    Usuario toUsuarioModel(UsuarioEntity usuarioEntity);

    List<Usuario> toUsuarioModelList(List<UsuarioEntity> usuarioEntityList);
}
