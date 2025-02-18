package com.management.product.config.security;


import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAuthenticationFilter authenticationFilter;

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests((authorize) -> {
                    authorize.requestMatchers("/graphiql").permitAll();
                    authorize.requestMatchers("/graphql").permitAll();
                    authorize.requestMatchers("/api/authentication/**").permitAll();
                    authorize.requestMatchers("/api/v1/users/**").permitAll();
                    authorize.requestMatchers(HttpMethod.GET, "/actuator/**").permitAll();
                    authorize.requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll();
                    authorize.requestMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll();
                    authorize.requestMatchers(
                            "/",                          // Racine nécessaire pour le dashboard
                            "/eureka",                    // Page principale
                            "/eureka/**",                 // Endpoints internes
                            "/eureka/css/**",             // Feuilles de style
                            "/eureka/js/**",              // Scripts JS
                            "/eureka/images/**",          // Images du dashboard
                            "/eureka/fonts/**",           // Polices
                            "/actuator/health",           // Endpoint requis par Eureka
                            "/actuator/info"              // Infos générales
                    ).permitAll();
                    authorize.anyRequest().authenticated();
                }).httpBasic(Customizer.withDefaults());
        http.exceptionHandling( exception -> exception
                .authenticationEntryPoint(authenticationEntryPoint));
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http.csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
//                .httpBasic(Customizer.withDefaults())
//                .build();
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


}
