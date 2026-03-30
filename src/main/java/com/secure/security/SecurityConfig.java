package com.secure.security;

import com.secure.security.utils.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {
    @Autowired
    private JwtFilter jwtFilter;
    @Bean
    public PasswordEncoder passwordEncoder() throws Exception{
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.
                csrf(AbstractHttpConfigurer::disable).
                cors(cors->cors.configurationSource(configuration())).
                authorizeHttpRequests((auth)-> auth
                        .requestMatchers("/auth/**", "/pages/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
    @Bean
    public CorsConfigurationSource configuration(){
        CorsConfiguration corsConfiguration=new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("*"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setMaxAge(3600L);
        corsConfiguration.setAllowCredentials(false);
        corsConfiguration.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);

        return source;
    }



}
