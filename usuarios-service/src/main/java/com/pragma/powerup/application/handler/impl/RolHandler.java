package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.RolRequestDto;
import com.pragma.powerup.application.dto.response.RolResponseDto;
import com.pragma.powerup.application.handler.IRolHandler;
import com.pragma.powerup.application.mapper.IRolRequestMapper;
import com.pragma.powerup.application.mapper.IRolResponseMapper;
import com.pragma.powerup.domain.api.IRolServicePort;
import com.pragma.powerup.domain.model.Rol;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RolHandler  implements IRolHandler {

    private final IRolServicePort rolServicePort;

    private final IRolRequestMapper rolRequestMapper;

    private final IRolResponseMapper rolResponseMapper;
    @Override
    public void guardarRol(RolRequestDto rolRequestDto) {
        Rol rol = rolRequestMapper.toRol(rolRequestDto);
        rolServicePort.guardarRol(rol);
    }

    @Override
    public RolResponseDto obtenerRolPorId(Long id) {
        RolResponseDto rolResponseDto = rolResponseMapper.toResponse(rolServicePort.obtenerRolPorId(id));
        return rolResponseDto;
    }

    @Override
    public List<RolResponseDto> obtenerTodosRoles() {
        return rolResponseMapper.toResposeList(rolServicePort.obtenerTodosRoles());
    }

    @Override
    public void eliminarRolPorId(Long id) {
        rolServicePort.eliminarRolPorId(id);

    }
}
