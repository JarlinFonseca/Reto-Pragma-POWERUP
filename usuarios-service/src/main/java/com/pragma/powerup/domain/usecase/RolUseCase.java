package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IRolServicePort;
import com.pragma.powerup.domain.model.Rol;
import com.pragma.powerup.domain.spi.persistence.IRolPersistencePort;

import java.util.List;

public class RolUseCase implements IRolServicePort {

    private final IRolPersistencePort rolPersistencePort;

    public RolUseCase(IRolPersistencePort rolPersistencePort) {
        this.rolPersistencePort = rolPersistencePort;
    }


    @Override
    public void guardarRol(Rol rol) {
        rolPersistencePort.guardarRol(rol);
    }

    @Override
    public Rol obtenerRolPorId(Long id) {
        return rolPersistencePort.obtenerRolPorId(id);
    }

    @Override
    public List<Rol> obtenerTodosRoles() {
        return rolPersistencePort.obtenerTodosRoles();
    }

    @Override
    public void eliminarRolPorId(Long id) {
        rolPersistencePort.eliminarRolPorId(id);
    }
}
