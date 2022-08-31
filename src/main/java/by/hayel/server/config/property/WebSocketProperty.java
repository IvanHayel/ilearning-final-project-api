package by.hayel.server.config.property;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "web-socket")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WebSocketProperty {
  String app;
  String broker;
  String endpoint;
  String allowedOrigins;
  String user;
}
