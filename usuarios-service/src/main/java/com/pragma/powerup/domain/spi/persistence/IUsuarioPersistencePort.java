package com.pragma.powerup.domain.spi.persistence;

import com.pragma.powerup.domain.model.Usuario;

import java.util.List;

public interface IUsuarioPersistencePort {

    Usuario saveUser(Usuario usuario);

    Usuario getUserById(Long id);

    Usuario getUserByCorreo(String correo);

    Boolean existsUserById(Long id);

    List<Usuario> getAllUsers();

    void deleteUserById(Long id);

}
