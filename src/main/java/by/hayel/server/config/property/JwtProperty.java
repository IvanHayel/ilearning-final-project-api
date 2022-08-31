package by.hayel.server.config.property;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtProperty {
  String secret;
  long expirationTime;
  String headerName;
  String type;
  @NestedConfigurationProperty Header header;
  @NestedConfigurationProperty RefreshToken refresh;

  @Getter
  @Setter
  @FieldDefaults(level = AccessLevel.PRIVATE)
  private static class Header {
    String name;
  }

  @Getter
  @Setter
  @FieldDefaults(level = AccessLevel.PRIVATE)
  private static class RefreshToken {
    long expirationTime;
  }
}
