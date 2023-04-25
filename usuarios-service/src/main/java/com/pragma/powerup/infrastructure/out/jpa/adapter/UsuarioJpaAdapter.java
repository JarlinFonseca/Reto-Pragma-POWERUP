package com.pragma.powerup.infrastructure.out.jpa.adapter;


import com.pragma.powerup.domain.model.Usuario;
import com.pragma.powerup.domain.spi.persistence.IUsuarioPersistencePort;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup.infrastructure.out.jpa.entity.UsuarioEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IUsuarioEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UsuarioJpaAdapter implements IUsuarioPersistencePort {

    private final IUsuarioRepository usuarioRepository;
    private  final IUsuarioEntityMapper usuarioEntityMapper;



    @Override
    public Usuario saveUser(Usuario usuario) {
        UsuarioEntity usuarioEntity = usuarioRepository.save(usuarioEntityMapper.toEntity(usuario));
        return usuarioEntityMapper.toUsuarioModel(usuarioEntity);
    }

    @Override
    public Usuario getUserById(Long id) {
        Optional<UsuarioEntity> usuarioEntityOptional= usuarioRepository.findById(id);
        UsuarioEntity usuarioEntity =  usuarioEntityOptional.orElse(null) ;
        return usuarioEntityMapper.toUsuarioModel(usuarioEntity) ;
    }

    @Override
    public Usuario getUserByCorreo(String correo) {
        Optional<UsuarioEntity> usuarioEntityOptional = usuarioRepository.findByCorreo(correo);
        UsuarioEntity usuarioEntity = usuarioEntityOptional.orElse(null);
        return usuarioEntityMapper.toUsuarioModel(usuarioEntity);
    }

    @Override
    public Boolean existsUserById(Long id) {
        return  usuarioRepository.existsById(id);
    }

    @Override
    public List<Usuario> getAllUsers() {
        List<UsuarioEntity> entityList = usuarioRepository.findAll();
        if(entityList.isEmpty()){
            throw new NoDataFoundException();
        }
        return usuarioEntityMapper.toUsuarioModelList(entityList);
    }

    @Override
    public void deleteUserById(Long id) {

          usuarioRepository.deleteById(id);
    }


}
