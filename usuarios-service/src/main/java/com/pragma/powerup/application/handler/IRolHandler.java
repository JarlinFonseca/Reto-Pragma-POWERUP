package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.RolRequestDto;
import com.pragma.powerup.application.dto.response.RolResponseDto;

import java.util.List;

public interface IRolHandler {

    void guardarRol(RolRequestDto rolRequestDto);

    RolResponseDto obtenerRolPorId(Long id);

    List<RolResponseDto> obtenerTodosRoles();

    void eliminarRolPorId(Long id);
}
