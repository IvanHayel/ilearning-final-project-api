package by.hayel.server.config.property;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cors")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CorsProperty {
  String mapping;
  boolean allowCredentials;
  List<String> allowedOrigins;
  List<String> allowedMethods;
  List<String> allowedHeaders;
}
