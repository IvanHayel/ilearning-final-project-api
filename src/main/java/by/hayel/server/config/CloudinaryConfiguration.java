package by.hayel.server.config;

import by.hayel.server.config.property.CloudinaryProperty;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CloudinaryConfiguration {
  private static final String CLOUD_NAME = "cloud_name";
  private static final String API_KEY = "api_key";
  private static final String API_SECRET = "api_secret";

  CloudinaryProperty property;

  @Bean
  public Cloudinary cloudinary() {
    var properties =
        ObjectUtils.asMap(
            CLOUD_NAME, property.getCloudName(),
            API_KEY, property.getApiKey(),
            API_SECRET, property.getApiSecret());
    return new Cloudinary(properties);
  }
}
