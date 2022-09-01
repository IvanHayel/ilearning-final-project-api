package by.hayel.server.model.entity.collection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum CollectionsSortStrategy {
  DEFAULT("updateDate"),
  CREATE_DATE("createDate"),
  UPDATE_DATE("updateDate"),
  NAME("name");

  String fieldName;
}
