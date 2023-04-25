package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.UsuarioResponseDto;
import com.pragma.powerup.domain.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUsuarioResponseMapper {
    UsuarioResponseDto toResponse(Usuario usuario);


    List<UsuarioResponseDto> toResponseList(List<Usuario> usuarioModelList);

    //Usuario toUsuario(UsuarioResponseDto usuarioResponseDto);
}
