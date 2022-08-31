package by.hayel.server.config.property;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cloudinary")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CloudinaryProperty {
  String cloudName;
  String apiKey;
  String apiSecret;
}
