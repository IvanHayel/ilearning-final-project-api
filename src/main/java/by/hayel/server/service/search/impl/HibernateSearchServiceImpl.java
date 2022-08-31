package by.hayel.server.service.search.impl;

import by.hayel.server.exception.search.HibernateSearchInitializationException;
import by.hayel.server.mapper.collection.CollectionItemMapper;
import by.hayel.server.mapper.collection.UserCollectionMapper;
import by.hayel.server.model.entity.BaseEntity;
import by.hayel.server.model.entity.collection.CollectionItem;
import by.hayel.server.model.entity.collection.UserCollection;
import by.hayel.server.model.entity.collection.dto.CollectionItemDto;
import by.hayel.server.model.entity.collection.dto.UserCollectionDto;
import by.hayel.server.model.search.CollectionItemSearchFields;
import by.hayel.server.model.search.SortStrategy;
import by.hayel.server.model.search.UserCollectionSearchFields;
import by.hayel.server.service.search.HibernateSearchService;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HibernateSearchServiceImpl implements HibernateSearchService {
  final UserCollectionMapper collectionMapper;
  final CollectionItemMapper itemMapper;

  @PersistenceContext EntityManager entityManager;

  boolean isInitialized = false;

  public void initializeHibernateSearch() {
    try {
      var searchSession = Search.session(entityManager);
      var indexer = searchSession.massIndexer(UserCollection.class, CollectionItem.class);
      indexer.startAndWait();
      isInitialized = true;
    } catch (InterruptedException e) {
      throw new HibernateSearchInitializationException();
    }
  }

  private <T> SearchResult<T> searchAll(Class<T> type, String searchTerm, String... fields) {
    if (!isInitialized) initializeHibernateSearch();
    var searchSession = Search.session(entityManager);
    return searchSession
        .search(type)
        .where(field -> field.match().fields(fields).matching(searchTerm))
        .fetchAll();
  }

  @Override
  @Transactional
  public List<UserCollectionDto> searchForCollections(String searchTerm) {
    var result =
        searchAll(UserCollection.class, searchTerm, UserCollectionSearchFields.getAllNames());
    return collectionMapper.userCollectionsToUserCollectionDtos(result.hits());
  }

  @Override
  @Transactional
  public List<UserCollectionDto> searchForCollectionsByTheme(String theme) {
    var result =
        searchAll(
            UserCollection.class,
            theme,
            UserCollectionSearchFields.COLLECTION_THEME_NAME.getName());
    return collectionMapper.userCollectionsToUserCollectionDtos(result.hits());
  }

  @Override
  @Transactional
  public List<CollectionItemDto> searchForItems(String searchTerm) {
    var result =
        searchAll(CollectionItem.class, searchTerm, CollectionItemSearchFields.getAllNames());
    return itemMapper.collectionItemsToCollectionItemDtos(result.hits());
  }

  @Override
  @Transactional
  public List<CollectionItemDto> searchForItemsByTag(String tag) {
    var result =
        searchAll(CollectionItem.class, tag, CollectionItemSearchFields.ITEM_TAGS_NAME.getName());
    return itemMapper.collectionItemsToCollectionItemDtos(result.hits());
  }

  @Override
  public <T extends BaseEntity> List<T> sort(List<T> list, SortStrategy sortStrategy) {
    return null;
  }
}
