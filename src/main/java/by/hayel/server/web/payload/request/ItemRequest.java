package by.hayel.server.web.payload.request;

import by.hayel.server.model.entity.collection.ItemField;
import by.hayel.server.web.payload.ClientRequest;
import java.util.List;
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
public class ItemRequest implements ClientRequest {
  @Nullable Long id;
  @NotBlank String name;
  @NotBlank String image;
  @NotNull List<String> tags;
  @Nullable List<ItemField> fields;
}
