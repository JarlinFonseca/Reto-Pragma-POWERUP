package com.pragma.powerup.application.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantResponseDto {
    private Long id;
    private String nombre;
    private String nit;
    private String direccion;
    private String telefono;
    private String urlLogo;
    private Long idPropietario;
}
