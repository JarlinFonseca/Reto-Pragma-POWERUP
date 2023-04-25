package com.pragma.powerup.application.dto.response;

import com.pragma.powerup.application.dto.request.DishRequestDto;
import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.infrastructure.out.jpa.entity.DishEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
     //private List<DishModel> platos;
}
