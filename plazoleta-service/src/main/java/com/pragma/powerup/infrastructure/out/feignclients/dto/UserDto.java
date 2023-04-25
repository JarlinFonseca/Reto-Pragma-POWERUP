package com.pragma.powerup.infrastructure.out.feignclients.dto;

import com.pragma.powerup.infrastructure.out.feignclients.dto.RolDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private Long id;
    private String nombre;
    private String apellido;
    private String documentoDeIdentidad;
    private String celular;
    private String correo;
    private String clave;
    private RolDto rol;


}
