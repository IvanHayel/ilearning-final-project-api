package by.hayel.server.web.payload.request;

import by.hayel.server.model.entity.collection.Theme;
import by.hayel.server.web.payload.ClientRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CollectionRequest implements ClientRequest {
  @Nullable Long id;
  @NotBlank String name;
  @NotNull Theme theme;
  @NotBlank String image;
}
