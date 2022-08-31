package by.hayel.server.model.search;

import java.util.Arrays;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum CollectionItemSearchFields {
  ITEM_NAME("name"),
  ITEM_FIELDS_NAME("fields.name"),
  ITEM_FIELDS_VALUE("fields.value"),
  ITEM_COMMENTS_CONTENT("comments.content"),
  ITEM_TAGS_NAME("tags.name");

  String name;

  public static String[] getAllNames() {
    return Arrays.stream(CollectionItemSearchFields.values())
        .map(CollectionItemSearchFields::getName)
        .toArray(String[]::new);
  }
}
