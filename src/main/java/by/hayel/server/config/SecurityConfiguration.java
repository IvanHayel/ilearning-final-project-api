package by.hayel.server.config;

import by.hayel.server.config.handler.OAuth2AuthenticationSuccessHandler;
import by.hayel.server.config.property.ClientProperty;
import by.hayel.server.service.security.impl.ServerOauth2UserService;
import by.hayel.server.web.filter.security.AuthenticationTokenFilter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecurityConfiguration {
  AuthenticationEntryPoint unauthorizedHandler;
  AuthenticationTokenFilter tokenFilter;
  ServerOauth2UserService oAuth2UserService;
  ClientProperty clientProperty;
  OAuth2AuthenticationSuccessHandler successHandler;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.httpBasic().disable()
        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().authorizeRequests()
        .antMatchers("/api/auth/**", "/oauth2/**", "/api/search/**", "/ws/**").permitAll()
        .antMatchers("/actuator/**").hasAnyRole("ADMIN", "ROOT")
        .antMatchers(HttpMethod.GET, "/api/collections/**").permitAll()
        .anyRequest().authenticated();
    http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
    http.oauth2Login()
        .userInfoEndpoint().userService(oAuth2UserService)
        .and().successHandler(successHandler).failureUrl(clientProperty.getUrl());
    http.cors().and().csrf().disable();
    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
      throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
