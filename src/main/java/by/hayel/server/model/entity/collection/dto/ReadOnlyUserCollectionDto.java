package by.hayel.server.model.entity.collection.dto;

import by.hayel.server.model.entity.user.dto.ReadOnlyUserDto;
import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReadOnlyUserCollectionDto implements Serializable {
  @Serial private static final long serialVersionUID = 1L;

  Long id;
  ZonedDateTime createDate;
  ZonedDateTime updateDate;
  String name;
  ThemeDto theme;
  ReadOnlyUserDto owner;
}
