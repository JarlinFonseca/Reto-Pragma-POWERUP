package com.pragma.powerup.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SmsMessageRequestDto {

    @NotBlank(message = "El numero celular es requerido")
    @Pattern(regexp = "^\\+57\\d{10}$", message = "El numero debe empezar con +57 seguido de 10 digitos (numero colombiano)")
    private String numero;

    @NotBlank(message = "El mensaje es requerido")
    private String mensaje;
}
