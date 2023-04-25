package com.pragma.powerup.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DishModel {

    private Long id;
    private String nombre;
    private String precio;
    private String descripcion;
    private String urlImagen;
    private Boolean activo;
    private RestaurantModel restauranteId;
    private CategoryModel categoriaId;


}
