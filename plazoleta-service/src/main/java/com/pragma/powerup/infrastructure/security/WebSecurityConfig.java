package com.pragma.powerup.infrastructure.security;

        import lombok.AllArgsConstructor;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
        import org.springframework.security.config.annotation.web.builders.HttpSecurity;
        import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
        import org.springframework.security.config.http.SessionCreationPolicy;
        import org.springframework.security.core.userdetails.UserDetailsService;
        import org.springframework.security.web.SecurityFilterChain;
        import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JWTAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception{
        return  http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/swagger-ui/**", "/swagger-resources/**","/v3/api-docs/**", "/v2/api-docs/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


}
