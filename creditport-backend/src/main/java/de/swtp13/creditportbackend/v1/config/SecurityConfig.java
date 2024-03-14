package de.swtp13.creditportbackend.v1.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private static final String API_VERSION = "";
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        (authorizeHttpRequests) -> authorizeHttpRequests
                                .requestMatchers(API_VERSION+"/users/update/password")
                                .authenticated()
                                .requestMatchers(API_VERSION+"/users/**")
                                .hasAuthority("ADMIN")
                                .requestMatchers(API_VERSION+"/test-endpoint/open")
                                .permitAll()
                                .requestMatchers(HttpMethod.POST,
                                        API_VERSION + "/universities",
                                        API_VERSION + "/procedures",
                                        API_VERSION + "/modules/external",
                                        API_VERSION + "/auth/login")

                                .permitAll()
                                .requestMatchers(HttpMethod.GET,
                                        API_VERSION + "/universities",
                                        API_VERSION + "/modules/external",
                                        API_VERSION + "/modules/internal",
                                        API_VERSION + "/courses",
                                        API_VERSION + "/procedures/??????")
                                .permitAll()
                                .requestMatchers("/swagger-ui/**", "/api-docs/**")
                                .permitAll()
                                .requestMatchers(API_VERSION + "/pdf/**")
                                .permitAll()
                                .requestMatchers(API_VERSION + "/actuator/health")
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                .sessionManagement(
                        (sessionManagement) -> sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
}
