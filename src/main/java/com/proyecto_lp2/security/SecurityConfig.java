package com.proyecto_lp2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(withDefaults()).
                csrf(csrf -> csrf.disable()) // Deshabilita CSRF para la API de prueba
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(HttpMethod.GET, "/api/productos/**").permitAll() // GET es público
                                .requestMatchers(HttpMethod.POST, "/api/productos/**").permitAll() // POST solo ADMIN
                                .requestMatchers(HttpMethod.POST, "/api/usuarios/registrar").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/productos/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/estados/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/categorias/**").permitAll()
                                .anyRequest().authenticated()
                )
                .httpBasic(withDefaults()); // método de autenticación .formLogin()
		return http.build();
	}

	@Autowired
    private PasswordEncoder passwordEncoder;
	
	// Usuario en memoria de ejemplo
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user = User.builder()
				.username("admin")
				.password(passwordEncoder.encode("1234"))
				.roles("ADMIN").build();
		return new InMemoryUserDetailsManager(user);
	}
}