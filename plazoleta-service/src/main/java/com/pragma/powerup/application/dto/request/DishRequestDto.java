package com.pragma.powerup.application.dto.request;


import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class DishRequestDto {

    @NotBlank(message = "El nombre es requerido")
    private String nombre;
    @NotBlank(message = "El precio es requerido")
    @Pattern(regexp = "^[1-9]\\d*$", message = "El precio debe ser un número entero positivo mayor a cero")
    private String precio;
    @NotBlank(message = "La descripcion es requerida")
    private String descripcion;
    @NotBlank(message = "La urlImagen es requerida")
    private String urlImagen;
   // @NotNull(message = "El activo es requerido (true(1) o false)")
    private Boolean activo;

    @NotNull(message = "El restaurante_id no puede ser nulo")
    @Min(value = 1, message = "El restaurante_id debe ser mayor a cero")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private Long restauranteId;

    @NotNull(message = "La categoria_id no puede ser nulo")
    @Min(value = 1, message = "La categoria_id debe ser mayor a cero")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private Long categoriaId;
}
