/***************************************************************
 * Author       :
 * Created Date :
 * Version      :
 * History  :
 * *************************************************************/
package com.testing._auth.githubtesting.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * SecurityConfig Class.
 * <p>
 * </p>
 *
 * @author
 */

@Configuration
public class SecurityConfig {

    private final GlobalCorsConfig globalCorsConfig;

    public SecurityConfig(GlobalCorsConfig globalCorsConfig){
        this.globalCorsConfig = globalCorsConfig;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(globalCorsConfig.corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login/**", "/oauth2/**", "/calendars/**","/drive/**").permitAll()
                        .requestMatchers("/getRepos", "/getCalendarEvent").authenticated()
                        .anyRequest().permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                )
//                .oauth2ResourceServer(resourceServer -> resourceServer
//                        .jwt(withDefaults())  // enable JWT validation for bearer tokens
//                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }



}
