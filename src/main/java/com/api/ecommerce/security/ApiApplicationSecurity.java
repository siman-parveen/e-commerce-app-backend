package com.api.ecommerce.security;

import com.api.ecommerce.repo.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ApiApplicationSecurity {

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public UserDetailsService userDetailsService(
            UserRepo userRepo,
            PasswordEncoder passwordEncoder,
            @Value("${app.security.admin.email:}") String adminEmail,
            @Value("${app.security.admin.password:}") String adminPassword) {
        String encodedAdminPassword = adminPassword.isBlank()
                ? ""
                : passwordEncoder.encode(adminPassword);
        return email -> {
            if (!adminEmail.isBlank() && !adminPassword.isBlank()
                    && adminEmail.equalsIgnoreCase(email)) {
                return org.springframework.security.core.userdetails.User
                        .withUsername(adminEmail)
                        .password(encodedAdminPassword)
                        .roles("ADMIN")
                        .build();
            }

            return userRepo.findByEmailId(email)
                    .map(user -> org.springframework.security.core.userdetails.User
                            .withUsername(user.getExternalId())
                            .password(user.getPassword())
                            .roles("USER")
                            .build())
                    .orElseThrow(() -> new UsernameNotFoundException("Invalid email or password"));
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            SecurityContextRepository securityContextRepository) throws Exception {
        http
            .csrf(csrf -> csrf.csrfTokenRepository(new HttpSessionCsrfTokenRepository()))
            .cors(cors -> {})
            .securityContext(context -> context
                    .securityContextRepository(securityContextRepository)
                    .requireExplicitSave(true))
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(HttpMethod.POST, "/api/user/signup", "/api/user/login", "/api/logout").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/public/**", "/api/product/*/reviews").permitAll()
                    .requestMatchers(HttpMethod.GET, "/actuator/health").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/auth/csrf").permitAll()
                    .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                    .requestMatchers("/api/admin/**", "/h2-console/**").hasRole("ADMIN")
                    .requestMatchers("/api/user/**").hasRole("USER")
                    .requestMatchers("/api/auth/**").authenticated()
                    .anyRequest().denyAll())
            .exceptionHandling(exceptions -> exceptions
                    .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
            .logout(logout -> logout
                    .logoutUrl("/api/logout")
                    .logoutSuccessHandler((request, response, authentication) ->
                            response.setStatus(HttpStatus.NO_CONTENT.value()))
                    .permitAll())
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
