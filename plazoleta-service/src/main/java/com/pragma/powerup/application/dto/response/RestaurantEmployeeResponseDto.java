package com.pragma.powerup.application.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantEmployeeResponseDto {

    private Long id;

    private String restaurantId;

    private String personId;
}
