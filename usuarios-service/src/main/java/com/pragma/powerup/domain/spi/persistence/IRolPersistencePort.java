package com.pragma.powerup.domain.spi.persistence;

import com.pragma.powerup.domain.model.Rol;
import com.pragma.powerup.domain.model.Usuario;

import java.util.List;

public interface IRolPersistencePort {

    Rol guardarRol(Rol rol);

    Rol obtenerRolPorId(Long id);

    List<Rol> obtenerTodosRoles();

    void eliminarRolPorId(Long id);
}
