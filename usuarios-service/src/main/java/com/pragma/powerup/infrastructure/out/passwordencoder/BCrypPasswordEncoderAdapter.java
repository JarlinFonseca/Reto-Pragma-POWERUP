package com.pragma.powerup.infrastructure.out.passwordencoder;

import com.pragma.powerup.domain.spi.passwordencoder.IUsuarioPasswordEncoderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class BCrypPasswordEncoderAdapter implements IUsuarioPasswordEncoderPort {

    private final PasswordEncoder passwordEncoder =  new BCryptPasswordEncoder();

    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
