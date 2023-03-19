package com.pi.kursy.security.configuration;

import com.pi.kursy.security.shared.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
class SecurityConfig {

    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().disable().csrf().disable().formLogin().disable().httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                // authentication
                .requestMatchers(HttpMethod.POST, "/authentication/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/authentication/refresh").permitAll()
                .requestMatchers(HttpMethod.POST, "/authentication/logout").permitAll()
                // users
                .requestMatchers(HttpMethod.POST, "/users/privileged").hasAuthority(RoleEnum.ADMIN.name())
                .requestMatchers(HttpMethod.POST, "/users/register").permitAll()
                .requestMatchers(HttpMethod.PATCH, "/users/password").authenticated()
                // categories
                .requestMatchers(HttpMethod.POST, "/categories").hasAuthority(RoleEnum.ADMIN.name())
                .requestMatchers(HttpMethod.GET, "/categories").permitAll()

                // courses
                .requestMatchers(HttpMethod.POST, "/courses").hasAuthority(RoleEnum.ADMIN.name())
                .requestMatchers(HttpMethod.GET, "/courses").permitAll()
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

}
