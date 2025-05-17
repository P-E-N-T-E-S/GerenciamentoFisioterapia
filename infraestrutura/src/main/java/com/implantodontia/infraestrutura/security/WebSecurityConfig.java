package com.implantodontia.infraestrutura.security;

import com.implantodontia.infraestrutura.security.jwt.AuthEntryPointJwt;
import com.implantodontia.infraestrutura.security.jwt.AuthFiltherToken;
import com.implantodontia.infraestrutura.security.jwt.JwtUtils;
import com.implantodontia.infraestrutura.security.userdetail.UsuarioDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler; // AuthEntryPointJwt bean

    @Autowired
    private UsuarioDetailsService usuarioDetailsService; // UsuarioDetailsService bean

    @Autowired
    private JwtUtils jwtUtils; // Certifique-se de injetar o JwtUtils também

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthFiltherToken authFilterToken() {
        return new AuthFiltherToken(usuarioDetailsService, jwtUtils); // Passando as dependências
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors(Customizer.withDefaults());
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/obras/lib/adicionar").permitAll()
                        .requestMatchers("/emprestimo").hasAuthority("ROLE_3")
                        .requestMatchers("/exemplares").hasAuthority("ROLE_3")
                        .requestMatchers("/lib/**").hasAuthority("ROLE_3")
                        .anyRequest().authenticated());

        http.addFilterBefore(authFilterToken(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

