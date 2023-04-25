package com.pragma.powerup.application.dto.response;

import com.pragma.powerup.domain.model.Rol;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponseDto {
    private Long id;
    private String nombre;
    private String apellido;
    private String documentoDeIdentidad;
    private  String celular;
    private String correo;
    private String clave;

    private Rol rol;
}
