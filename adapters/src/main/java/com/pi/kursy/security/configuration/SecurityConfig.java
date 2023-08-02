package com.pi.kursy.security.configuration;

import com.pi.kursy.security.shared.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
class SecurityConfig {

    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().formLogin().disable().httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()

                // authentication
                .requestMatchers(HttpMethod.POST, "/authentication/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/authentication/refresh").permitAll()
                .requestMatchers(HttpMethod.POST, "/authentication/logout").permitAll()

                // users
                .requestMatchers(HttpMethod.POST, "/users/register").permitAll()
                .requestMatchers(HttpMethod.PATCH, "/users/password").authenticated()
                .requestMatchers(HttpMethod.GET, "/users").hasAuthority(RoleEnum.ADMIN.name())

                // categories
                .requestMatchers(HttpMethod.POST, "/categories").hasAuthority(RoleEnum.ADMIN.name())
                .requestMatchers(HttpMethod.GET, "/categories").permitAll()

                // courses
                .requestMatchers(HttpMethod.POST, "/courses").hasAuthority(RoleEnum.ADMIN.name())
                .requestMatchers(HttpMethod.GET, "/courses").permitAll()
                .requestMatchers(HttpMethod.GET, "/courses/*/buy").authenticated()
                .requestMatchers(HttpMethod.GET, "/courses/*").permitAll()
                .requestMatchers(HttpMethod.PATCH, "/courses/*").hasAuthority(RoleEnum.ADMIN.name())

                // sections
                .requestMatchers(HttpMethod.POST, "/courses/*/sections").hasAuthority(RoleEnum.TEACHER.name())

                .anyRequest().permitAll()
                .and()
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public CorsConfiguration corsConfiguration() {
        CorsConfiguration cors = new CorsConfiguration().applyPermitDefaultValues();
        cors.setAllowedMethods(List.of("*"));
        cors.setExposedHeaders(List.of("Authorization", HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, HttpHeaders.CONTENT_DISPOSITION));
        return cors;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration());
        return source;
    }

}
