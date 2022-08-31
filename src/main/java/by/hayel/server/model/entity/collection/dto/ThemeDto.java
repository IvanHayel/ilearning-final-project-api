package by.hayel.server.model.entity.collection.dto;

import java.io.Serial;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ThemeDto implements Serializable {
  @Serial private static final long serialVersionUID = 1L;

  Long id;
  String name;
}
