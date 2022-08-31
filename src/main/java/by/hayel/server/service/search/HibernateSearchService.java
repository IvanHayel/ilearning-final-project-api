package by.hayel.server.service.search;

import by.hayel.server.model.entity.BaseEntity;
import by.hayel.server.model.entity.collection.dto.CollectionItemDto;
import by.hayel.server.model.entity.collection.dto.UserCollectionDto;
import by.hayel.server.model.search.SortStrategy;
import java.util.List;

public interface HibernateSearchService {
  List<UserCollectionDto> searchForCollections(String searchTerm);

  List<UserCollectionDto> searchForCollectionsByTheme(String theme);

  List<CollectionItemDto> searchForItems(String searchTerm);

  List<CollectionItemDto> searchForItemsByTag(String tag);

  <T extends BaseEntity> List<T> sort(List<T> list, SortStrategy sortStrategy);
}
