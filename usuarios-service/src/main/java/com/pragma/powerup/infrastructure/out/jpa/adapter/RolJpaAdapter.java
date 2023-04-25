package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.Rol;
import com.pragma.powerup.domain.spi.persistence.IRolPersistencePort;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup.infrastructure.out.jpa.entity.RolEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRolEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRolRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class RolJpaAdapter implements IRolPersistencePort {


    private  final IRolRepository rolRepository;
    private  final IRolEntityMapper rolEntityMapper;


    @Override
    public Rol guardarRol(Rol rol) {
        RolEntity rolEntity = rolRepository.save(rolEntityMapper.toEntity(rol));
        return rolEntityMapper.toRolModel(rolEntity);
    }

    @Override
    public Rol obtenerRolPorId(Long id) {
        Optional<RolEntity> optionalRolEntity = rolRepository.findById(id);
        RolEntity rolEntity = optionalRolEntity.orElse(null);
        return rolEntityMapper.toRolModel(rolEntity);
    }

    @Override
    public List<Rol> obtenerTodosRoles() {
        List<RolEntity> rolEntityList = rolRepository.findAll();
        if(rolEntityList.isEmpty()){
           throw  new NoDataFoundException();
        }
        return rolEntityMapper.toRolModelList(rolEntityList);
    }

    @Override
    public void eliminarRolPorId(Long id) {
            rolRepository.deleteById(id);
    }
}
