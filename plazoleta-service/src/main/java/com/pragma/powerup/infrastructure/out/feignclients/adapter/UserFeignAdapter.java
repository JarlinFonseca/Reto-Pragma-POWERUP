package com.pragma.powerup.infrastructure.out.feignclients.adapter;

import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.feignclients.IUserFeignClientPort;
import com.pragma.powerup.infrastructure.out.feignclients.UserFeignClients;
import com.pragma.powerup.infrastructure.out.feignclients.dto.UserDto;
import com.pragma.powerup.infrastructure.out.feignclients.mapper.IUserDtoMapper;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class UserFeignAdapter implements IUserFeignClientPort {

    private final UserFeignClients userFeignClients;

    private  final IUserDtoMapper userDtoMapper;

    @Override
    public Boolean existsUserById(Long usuarioId) {
        return userFeignClients.existsUserById(usuarioId);
    }

    @Override
    public UserModel getUserById(Long usuarioId) {
        UserDto userDto =userFeignClients.getUserById(usuarioId);
        return userDtoMapper.toUserModel(userDto);
    }

    @Override
    public UserModel getUserByCorreo(String correo) {
        UserDto userDto= userFeignClients.getUserByCorreo(correo);
        return userDtoMapper.toUserModel(userDto);
    }
}
