package by.hayel.server.web.payload.response;

import by.hayel.server.model.entity.collection.dto.CollectionItemDto;
import by.hayel.server.model.entity.collection.dto.UserCollectionDto;
import by.hayel.server.web.payload.ServerResponse;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GlobalSearchResponse implements ServerResponse {
  List<UserCollectionDto> collections;
  List<CollectionItemDto> items;
}
