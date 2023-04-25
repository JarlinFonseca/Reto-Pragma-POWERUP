package com.pragma.powerup.infrastructure.out.feignclients;

import com.pragma.powerup.application.dto.response.UserResponseDto;
import com.pragma.powerup.infrastructure.out.feignclients.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuarios-service", url = "localhost:8081/api/v1/user")
public interface UserFeignClients {

    @GetMapping("/existsUserById/{id}")
    Boolean existsUserById(@PathVariable(value = "id") Long usuarioId);

    @GetMapping("/{id}")
    UserDto getUserById(@PathVariable(value = "id") Long usuarioId);

    @GetMapping("/email/{email}")
    UserDto getUserByCorreo(@PathVariable(value = "email") String correo);
}
