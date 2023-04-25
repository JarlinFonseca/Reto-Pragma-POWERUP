package com.pragma.powerup.application.dto.response;

import com.pragma.powerup.domain.model.Rol;
import com.pragma.powerup.domain.model.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RolResponseDto {

    private Long id;
    private  String nombre;
    private String descripcion;
    // private List<Usuario> usuarios;

}
