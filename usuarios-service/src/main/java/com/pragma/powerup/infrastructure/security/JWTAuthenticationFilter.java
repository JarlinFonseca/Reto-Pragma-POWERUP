package com.pragma.powerup.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        AuthCredentials authCredentials= new AuthCredentials();

        try {
            authCredentials = new ObjectMapper().readValue(request.getReader(), AuthCredentials.class);
        }catch (IOException e){

        }

        UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
                authCredentials.getEmail(),
                authCredentials.getPassword(),
                Collections.emptyList()
        );


        return getAuthenticationManager().authenticate(usernamePAT);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        UserDetailsImpl userDetails= (UserDetailsImpl) authResult.getPrincipal();
        //System.out.println(Arrays.stream(userDetails.getAuthorities().toArray()).findFirst().toString());
        //userDetails.getAuthorities().toString()
        Object[] authorities = userDetails.getAuthorities().toArray();
        System.out.println(authorities[0].toString());
        String token = TokenUtils.createToken(userDetails.getNombre(), userDetails.getUsername(),
                authorities[0].toString(), userDetails.getId());
        response.addHeader("Authorization", "Bearer "+token);
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }
}
