package by.hayel.server.config.property;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth2")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OAuth2Property {
  String redirect;
  String queryParameter;
}
