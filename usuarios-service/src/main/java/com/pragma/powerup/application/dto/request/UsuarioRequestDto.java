package com.pragma.powerup.application.dto.request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UsuarioRequestDto {
    @NotBlank(message = "El nombre es requerido")
    @Size(min = 2, message = "El nombre debe tener al menos 2 caracteres")
    private String nombre;
    @NotBlank(message = "El apellido es requerido")
    @Size(min = 2, message = "El apellido debe tener al menos 2 caracteres")
    private String apellido;
    @NotBlank(message = "El  documentoDeIdentidad es requerido")
    @Pattern(regexp = "\\d+", message = "El documentoDeIdentidad debe ser númerico")
    @Size(min = 5, message = "El documento de identidad debe tener al menos 5 numeros")
    private String documentoDeIdentidad;
    @NotBlank(message = "El celular es requerido")
    @Pattern(regexp = "^\\+?\\d{1,12}$", message = "El número de celular debe contener máximo 13 caracteres y puede contener el símbolo '+' al inicio")
    private  String celular;
    @NotBlank(message = "El correo electrónico es requerido")
    @Email(message = "El correo electrónico debe ser válido")
    private String correo;
    @NotBlank(message = "La clave es requerida")
    private String clave;
    private Long rol;


}
