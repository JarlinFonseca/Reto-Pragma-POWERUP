package com.pragma.powerup.application.dto.response;

import com.pragma.powerup.domain.model.CategoryModel;
import com.pragma.powerup.domain.model.RestaurantModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishResponseDto {

    private Long id;
    private String nombre;
    private String precio;
    private String descripcion;
    private String urlImagen;
    private Boolean activo;
    private RestaurantModel restauranteId;
    private CategoryModel categoriaId;
}
