package by.hayel.server.model.entity.collection.dto;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CollectionItemDto implements Serializable {
  @Serial private static final long serialVersionUID = 1L;

  Long id;
  ZonedDateTime createDate;
  ZonedDateTime updateDate;
  String name;
  String imageLink;
  ReadOnlyUserCollectionDto collection;
  List<ItemFieldDto> fields;
  List<CommentDto> comments;
  List<LikeDto> likes;
  Set<TagDto> tags;
}
