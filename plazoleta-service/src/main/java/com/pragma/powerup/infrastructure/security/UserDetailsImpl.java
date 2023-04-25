package com.pragma.powerup.infrastructure.security;

import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.infrastructure.out.feignclients.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final UserDto userDto;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userDto.getRol().getNombre()));
    }

    @Override
    public String getPassword() {
        return userDto.getClave();
    }

    @Override
    public String getUsername() {
        return userDto.getCorreo();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getNombre(){
        return userDto.getNombre();
    }

    public Long getId(){
        return userDto.getId();
    }
}
