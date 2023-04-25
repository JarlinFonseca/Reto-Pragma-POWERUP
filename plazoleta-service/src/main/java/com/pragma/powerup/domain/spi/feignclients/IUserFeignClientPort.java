package com.pragma.powerup.domain.spi.feignclients;

import com.pragma.powerup.domain.model.UserModel;


public interface IUserFeignClientPort {


    Boolean existsUserById(Long usuarioId);

    UserModel getUserById(Long usuarioId);

    UserModel getUserByCorreo(String correo);
}
