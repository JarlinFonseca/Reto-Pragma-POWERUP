package com.pragma.powerup.infrastructure.feignconfiguration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class SecurityFeignRequestInterceptor implements RequestInterceptor {

    private static  final  String AUTHORIZATION_HEADER = "Authorization";
    @Override
    public void apply(RequestTemplate template) {
        template.header(AUTHORIZATION_HEADER, getBearerTokenHeader());
    }

    public static String getBearerTokenHeader(){
        System.out.println(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization"));
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
    }
}
