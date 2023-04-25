package com.pragma.powerup.domain.spi.token;

public interface IToken {
    String getBearerToken();

    String getCorreo(String token);

    Long getUsuarioAutenticadoId(String token);

    String getUsuarioAutenticadoRol(String token);
}
