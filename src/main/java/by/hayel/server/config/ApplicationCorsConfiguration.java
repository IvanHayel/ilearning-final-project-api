package by.hayel.server.config;

import by.hayel.server.config.property.CorsProperty;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationCorsConfiguration {
  CorsProperty cors;

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    var source = new UrlBasedCorsConfigurationSource();
    var configuration = new CorsConfiguration();
    configuration.setAllowCredentials(cors.isAllowCredentials());
    configuration.setAllowedOriginPatterns(cors.getAllowedOrigins());
    configuration.setAllowedMethods(cors.getAllowedMethods());
    configuration.setAllowedHeaders(cors.getAllowedHeaders());
    source.registerCorsConfiguration(cors.getMapping(), configuration);
    return source;
  }
}
