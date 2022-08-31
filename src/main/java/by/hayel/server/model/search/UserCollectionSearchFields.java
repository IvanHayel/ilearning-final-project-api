package by.hayel.server.model.search;

import java.util.Arrays;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum UserCollectionSearchFields {
  COLLECTION_NAME("name"),
  COLLECTION_THEME_NAME("theme.name");

  String name;

  public static String[] getAllNames() {
    return Arrays.stream(UserCollectionSearchFields.values())
        .map(UserCollectionSearchFields::getName)
        .toArray(String[]::new);
  }
}
