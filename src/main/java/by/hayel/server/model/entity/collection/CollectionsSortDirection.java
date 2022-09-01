package by.hayel.server.model.entity.collection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort.Direction;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum CollectionsSortDirection {
  DEFAULT(Direction.DESC),
  ASC(Direction.ASC),
  DESC(Direction.DESC);

  Direction direction;
}
