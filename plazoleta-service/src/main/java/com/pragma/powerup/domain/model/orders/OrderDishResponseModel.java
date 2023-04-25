package com.pragma.powerup.domain.model.orders;

import com.pragma.powerup.domain.model.CategoryModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDishResponseModel {
    private Long id;
    private String nombre;
    private String precio;
    private String descripcion;
    private String urlImagen;
    private CategoryModel categoriaId;

    private String cantidad;
}
