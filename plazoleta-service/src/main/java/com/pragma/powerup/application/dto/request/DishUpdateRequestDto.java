package com.pragma.powerup.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class DishUpdateRequestDto {

    @NotBlank(message = "El precio es requerido")
    @Pattern(regexp = "^[1-9]\\d*$", message = "El precio debe ser un número entero positivo mayor a cero")
    private String precio;

    @NotBlank(message = "La descripccion es requerida")
    private String descripcion;

}
