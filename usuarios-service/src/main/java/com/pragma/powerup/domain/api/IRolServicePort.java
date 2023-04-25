package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Rol;

import java.util.List;

public interface IRolServicePort {

    void guardarRol(Rol rol);

    Rol obtenerRolPorId(Long id);

    List<Rol> obtenerTodosRoles();

    void eliminarRolPorId(Long id);
}
