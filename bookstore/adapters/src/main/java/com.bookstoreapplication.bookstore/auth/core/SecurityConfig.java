package com.bookstoreapplication.bookstore.auth.core;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
class SecurityConfig {

    private static final String[] NO_AUTH_ENDPOINTS = {
            "/api/v1/swagger-ui.html",
            "/api/v1/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/api/v1/h2-console",
            "/api/v1/h2-console/**",
            "/api/v1/login",
            "/api/v1/users/registration"
    };
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutService logoutService;

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type", "Access-Control-Allow-Headers"));
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:3002"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT", "PATCH", "OPTIONS", "HEAD"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setExposedHeaders(List.of("Authorization", "Cache-Control", "Content-Type", "Access-Control-Allow-Headers"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors()
                .and().csrf().disable()

                .authorizeHttpRequests()
                .antMatchers(NO_AUTH_ENDPOINTS).permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/books/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/books/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/books").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/books").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/users").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/users").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/users/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/v1/users/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/deliveries/send/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/deliveries/receive/**}").hasAuthority("ADMIN")
                .anyRequest().hasAnyAuthority("USER", "ADMIN")

                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                .logout()
                .logoutUrl("/api/v1/logout")
                .addLogoutHandler(logoutService)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                .and().build();
    }
}
