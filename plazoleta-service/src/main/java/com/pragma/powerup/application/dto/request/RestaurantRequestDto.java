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
public class RestaurantRequestDto {

    @NotBlank(message = "El nombre es requerido")
    @Pattern(regexp = "^(?=.*[a-zA-Z])[0-9a-zA-Z ]+$", message = "El nombre puede contener números con letras pero no solo números")
    private String nombre;
    @NotBlank(message = "El nit es requerido")
    @Pattern(regexp = "\\d+", message = "El nit debe ser númerico")
    private String nit;
    @NotBlank(message = "La direccion es requerida")
    private String direccion;
    @NotBlank(message = "El telefono es requerido")
    @Pattern(regexp = "^\\+?\\d{1,12}$", message = "El telefono debe contener máximo 13 caracteres y puede contener el símbolo '+' al inicio")
    private String telefono;
    @NotBlank(message = "La urlLogo es requerida")
    private String urlLogo;
    @NotNull(message = "El id_propietario no puede ser nulo")
    @Min(value = 1, message = "El id_propietario debe ser mayor a cero")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private Long idPropietario;

}
