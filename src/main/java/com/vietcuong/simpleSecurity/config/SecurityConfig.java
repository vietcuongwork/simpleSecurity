package com.vietcuong.simpleSecurity.config;


import com.vietcuong.simpleSecurity.service.UserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final CustomLogoutHandler logoutHandler;

    private final CustomLogoutSuccessHandler logoutSuccessHandler;

    public SecurityConfig(UserDetailsService userDetailsService,
                          JwtAuthenticationFilter jwtAuthenticationFilter,
                          JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                          CustomLogoutHandler logoutHandler,
                          CustomLogoutSuccessHandler logoutSuccessHandler,
                          CustomLogoutSuccessHandler logoutSuccessHandler1) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;

        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.logoutHandler = logoutHandler;
        this.logoutSuccessHandler = logoutSuccessHandler1;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
            throws Exception {

        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        request -> request.requestMatchers("/login/**",
                                                           "/register/**",
                                                           "/clientRegister" + "/**",
                                                           "/v3/api-docs",
                                                           "/v3/api-docs/**",
                                                           "/swagger-ui/**",
                                                           "/swagger-ui.html")
                                .permitAll().requestMatchers("/user/**")
                                .hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers("/admin/**")
                                .hasAuthority("ADMIN").anyRequest()
                                .authenticated())
                .userDetailsService(userDetailsService).sessionManagement(
                        session -> session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))
                .exceptionHandling(
                        exception -> exception.authenticationEntryPoint(
                                jwtAuthenticationEntryPoint))
                .addFilterBefore(jwtAuthenticationFilter,
                                 UsernamePasswordAuthenticationFilter.class)
                .logout(l -> l.logoutUrl("/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler(logoutSuccessHandler)).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
