package com.pragma.powerup.application.dto.response;

import com.pragma.powerup.domain.model.DishModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryResponseDto {
    private Long id;
    private String nombre;
    private String descripcion;
     //private List<DishModel> platos;
}
