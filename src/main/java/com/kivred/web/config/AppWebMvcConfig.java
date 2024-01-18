package com.kivred.web.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@RequiredArgsConstructor
public class AppWebMvcConfig implements WebMvcConfigurer {

    @Value("${user.ignore-path:/swagger/**}")
    private String[] ignorePath;

    @Value("${user.token}")
    private String token;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthHandlerInterceptor()).excludePathPatterns(ignorePath).pathMatcher(new AntPathMatcher());
    }

    public static class AuthHandlerInterceptor implements HandlerInterceptor {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authorizationHeader == null) {
                response.getWriter().write("Unauthorized");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return false;
            }
            return true;
        }
    }
}

