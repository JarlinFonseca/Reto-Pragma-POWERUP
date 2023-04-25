package com.pragma.powerup.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderDishRequestDto {
    @NotNull(message = "El ID del plato no puede ser nulo")
    private Long idPlatos;
    @NotNull(message = "La cantidad no puede ser nula")
    private Long cantidad;
}
