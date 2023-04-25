package com.pragma.powerup.application.dto.response;

import com.pragma.powerup.domain.model.CategoryModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDishResponseDto {
    private Long id;
    private String nombre;
    private String precio;
    private String descripcion;
    private String urlImagen;
    private CategoryModel categoriaId;

    private String cantidad;

}
