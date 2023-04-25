package com.pragma.powerup.infrastructure.out.token;

import com.pragma.powerup.domain.spi.token.IToken;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup.infrastructure.security.TokenUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class TokenAdapter implements IToken {
    @Override
    public String getBearerToken() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
    }

    @Override
    public String getCorreo(String token) {
         if(token==(null)) throw  new NoDataFoundException();
        return TokenUtils.getCorreo(token.replace("Bearer ",""));
    }

    @Override
    public Long getUsuarioAutenticadoId(String token) {
         if(token==(null)) throw  new NoDataFoundException();
        return TokenUtils.getUsuarioAutenticadoId(token.replace("Bearer ",""));
    }

    @Override
    public String getUsuarioAutenticadoRol(String token) {
         if(token==(null)) throw  new NoDataFoundException();
        return TokenUtils.getUsuarioAutenticadoRol(token.replace("Bearer ",""));
    }
}
