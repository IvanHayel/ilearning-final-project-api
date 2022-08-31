package by.hayel.server;

import by.hayel.server.config.property.CloudinaryProperty;
import by.hayel.server.config.property.CorsProperty;
import by.hayel.server.config.property.JwtProperty;
import by.hayel.server.config.property.OAuth2Property;
import by.hayel.server.config.property.WebSocketProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
  JwtProperty.class,
  CorsProperty.class,
  WebSocketProperty.class,
  CloudinaryProperty.class,
  OAuth2Property.class
})
public class ServerApplication {
  public static void main(String[] args) {
    SpringApplication.run(ServerApplication.class, args);
  }
}
