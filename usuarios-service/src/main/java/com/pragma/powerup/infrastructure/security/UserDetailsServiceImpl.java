package com.pragma.powerup.infrastructure.security;

import com.pragma.powerup.domain.model.Usuario;
import com.pragma.powerup.infrastructure.out.jpa.entity.UsuarioEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IUsuarioEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IUsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {


    private final IUsuarioRepository usuarioRepository;
    private final IUsuarioEntityMapper usuarioEntityMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuarioEntity usuarioEntity = usuarioRepository.findOneByCorreo(email).orElseThrow(()->new UsernameNotFoundException("El usuario con email "+email+" no existe."));
        Usuario  usuario= usuarioEntityMapper.toUsuarioModel(usuarioEntity);

        return new UserDetailsImpl(usuario);
    }
}
