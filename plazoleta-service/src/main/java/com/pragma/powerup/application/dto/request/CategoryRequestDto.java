package com.pragma.powerup.application.dto.request;

import com.pragma.powerup.domain.model.DishModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class CategoryRequestDto {
    @NotBlank(message = "El nombre es requerido")
    private String nombre;
    @NotBlank(message = "La descripcion es requerida")
    private String descripcion;
}
