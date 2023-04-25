package com.pragma.powerup.infrastructure.out.feignclients.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RolDto {
    private Long id;
    private String nombre;
    private String descripcion;
}
