package by.hayel.server.service.collection;

import by.hayel.server.model.entity.collection.UserCollection;
import by.hayel.server.model.entity.collection.dto.CollectionItemDto;
import by.hayel.server.model.entity.collection.dto.UserCollectionDto;
import by.hayel.server.web.payload.request.CollectionRequest;
import by.hayel.server.web.payload.request.ItemRequest;
import java.util.List;

public interface CollectionsService {
  List<UserCollectionDto> getGlobalCollections();

  List<UserCollectionDto> getUserCollections();

  UserCollectionDto getCollectionDtoByName(String name);

  UserCollection getCollectionById(Long id);

  UserCollection getCollectionByName(String name);

  List<CollectionItemDto> getCollectionItems(String name);

  void addCollection(CollectionRequest request);

  void updateCollection(CollectionRequest request);

  void deleteCollection(Long id);

  void addItemToCollection(String collectionName, ItemRequest request);

  void updateCollectionItem(String collectionName, ItemRequest request);

  void deleteItemFromCollection(String collectionName, Long itemId);

  void likeItem(Long itemId);

  CollectionItemDto getCollectionItem(String name, Long itemId);

  List<UserCollectionDto> getTopUserCollections(Long count);
}
