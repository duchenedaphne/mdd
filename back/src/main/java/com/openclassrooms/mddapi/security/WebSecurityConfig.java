package com.openclassrooms.mddapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

import com.openclassrooms.mddapi.security.jwt.AuthEntryPointJwt;
import com.openclassrooms.mddapi.security.jwt.AuthTokenFilter;

import lombok.RequiredArgsConstructor;

@EnableGlobalMethodSecurity(
    // securedEnabled = true,
    // jsr250Enabled = true,
    prePostEnabled = true
)
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;
  @Autowired
  private final AuthenticationProvider authenticationProvider;
  @Autowired
  private final AuthTokenFilter authTokenFilter;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .cors(withDefaults())
        .csrf(csrf -> csrf.disable())
        .exceptionHandling(
          handling -> handling
              .authenticationEntryPoint(unauthorizedHandler)
        )
        .authorizeRequests(
          requests -> requests
              .antMatchers("/api/auth/**").permitAll()
              .antMatchers("/api/**").authenticated()
              .anyRequest().authenticated()
        )
        .sessionManagement(
          management -> management
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(
          authTokenFilter, UsernamePasswordAuthenticationFilter.class
        );
    http
        .headers( 
            headers -> headers
            .frameOptions()
            .sameOrigin()
        );
    return http.build();
  }
}
