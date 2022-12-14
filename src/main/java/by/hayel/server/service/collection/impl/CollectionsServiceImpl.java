package by.hayel.server.service.collection.impl;

import static by.hayel.server.service.cloud.CloudinaryService.CLOUDINARY_PUBLIC_ID;
import static by.hayel.server.service.cloud.CloudinaryService.CLOUDINARY_SECURE_URL;

import by.hayel.server.exception.collection.CollectionNameAlreadyExistException;
import by.hayel.server.exception.collection.CollectionNotFoundException;
import by.hayel.server.exception.collection.ItemNameAlreadyExistException;
import by.hayel.server.exception.collection.LikeNotFoundException;
import by.hayel.server.mapper.collection.UserCollectionMapper;
import by.hayel.server.model.entity.collection.CollectionItem;
import by.hayel.server.model.entity.collection.CollectionsSortDirection;
import by.hayel.server.model.entity.collection.CollectionsSortStrategy;
import by.hayel.server.model.entity.collection.Like;
import by.hayel.server.model.entity.collection.UserCollection;
import by.hayel.server.model.entity.collection.dto.CollectionItemDto;
import by.hayel.server.model.entity.collection.dto.UserCollectionDto;
import by.hayel.server.model.entity.user.User;
import by.hayel.server.repository.collection.UserCollectionRepository;
import by.hayel.server.service.cloud.CloudinaryService;
import by.hayel.server.service.collection.CollectionsService;
import by.hayel.server.service.collection.CommentService;
import by.hayel.server.service.collection.ItemService;
import by.hayel.server.service.collection.LikeService;
import by.hayel.server.service.collection.TagService;
import by.hayel.server.service.collection.ThemeService;
import by.hayel.server.service.security.AuthenticationService;
import by.hayel.server.web.payload.ServerResponse;
import by.hayel.server.web.payload.request.CollectionRequest;
import by.hayel.server.web.payload.request.ItemRequest;
import by.hayel.server.web.payload.response.StatisticsResponse;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CollectionsServiceImpl implements CollectionsService {
  UserCollectionRepository repository;

  UserCollectionMapper mapper;

  AuthenticationService authenticationService;
  CloudinaryService cloudinaryService;
  ItemService itemService;
  ThemeService themeService;
  TagService tagService;
  LikeService likeService;
  CommentService commentService;

  @Override
  @Transactional
  public List<UserCollectionDto> getGlobalCollections(
      CollectionsSortDirection sortDirection, CollectionsSortStrategy sortStrategy) {
    var sort = Sort.by(sortDirection.getDirection(), sortStrategy.getFieldName());
    var collections = repository.findAll(sort);
    return mapper.userCollectionsToUserCollectionDtos(collections);
  }

  @Override
  @Transactional
  public List<UserCollectionDto> getUserCollections(
      CollectionsSortDirection sortDirection, CollectionsSortStrategy sortStrategy) {
    var user = authenticationService.getCurrentUser();
    var sort = Sort.by(sortDirection.getDirection(), sortStrategy.getFieldName());
    var collections = repository.findAllByOwner(user, sort);
    return mapper.userCollectionsToUserCollectionDtos(collections);
  }

  @Override
  @Transactional
  public UserCollectionDto getCollectionDtoByName(String name) {
    var collection = getCollectionByName(name);
    return mapper.userCollectionToUserCollectionDto(collection);
  }

  @Override
  @Transactional
  public UserCollection getCollectionByName(String name) {
    return repository.findByName(name).orElseThrow(CollectionNotFoundException::new);
  }

  @Override
  @Transactional
  public UserCollection getCollectionById(Long id) {
    return repository.findById(id).orElseThrow(CollectionNotFoundException::new);
  }

  @Override
  @Transactional
  public List<CollectionItemDto> getCollectionItems(
      String name, CollectionsSortDirection sortDirection, CollectionsSortStrategy sortStrategy) {
    var collection = getCollectionByName(name);
    var sort = Sort.by(sortDirection.getDirection(), sortStrategy.getFieldName());
    return itemService.getItemsByCollection(collection, sort);
  }

  @Override
  @Transactional
  public void addCollection(CollectionRequest request) {
    var user = authenticationService.getCurrentUser();
    if (isCollectionNameAlreadyExists(request.getName())) {
      throw new CollectionNameAlreadyExistException();
    }
    var collection = parseCollection(request, user);
    repository.save(collection);
  }

  private boolean isCollectionNameAlreadyExists(String name) {
    return repository.existsByName(name);
  }

  private UserCollection parseCollection(CollectionRequest request, User owner) {
    var collection = new UserCollection();
    themeService.addThemeIfNotExist(request.getTheme());
    var theme = themeService.get(request.getTheme().getName());
    var cloudinaryResponse = cloudinaryService.sendToCloud(request.getImage().getBytes());
    collection.setId(request.getId());
    collection.setName(request.getName());
    collection.setTheme(theme);
    collection.setOwner(owner);
    collection.setImageLink(cloudinaryResponse.get(CLOUDINARY_SECURE_URL));
    collection.setImagePublicId(cloudinaryResponse.get(CLOUDINARY_PUBLIC_ID));
    return collection;
  }

  @Override
  @Transactional
  public void updateCollection(CollectionRequest request) {
    var user = authenticationService.getCurrentUser();
    var collectionToUpdate = getCollectionById(request.getId());
    if (isUpdatedNameNotUnique(request.getName(), collectionToUpdate.getName())) {
      throw new CollectionNameAlreadyExistException();
    }
    if (authenticationService.isPermissible(user, collectionToUpdate.getOwner())) {
      cloudinaryService.removeByPublicId(collectionToUpdate.getImagePublicId());
      parseCollection(request, collectionToUpdate);
      repository.save(collectionToUpdate);
    }
  }

  private void parseCollection(CollectionRequest request, UserCollection collectionToUpdate) {
    themeService.addThemeIfNotExist(request.getTheme());
    var cloudinaryResponse = cloudinaryService.sendToCloud(request.getImage().getBytes());
    var theme = themeService.get(request.getTheme().getName());
    collectionToUpdate.setTheme(theme);
    collectionToUpdate.setName(request.getName());
    collectionToUpdate.setImageLink(cloudinaryResponse.get(CLOUDINARY_SECURE_URL));
    collectionToUpdate.setImagePublicId(cloudinaryResponse.get(CLOUDINARY_PUBLIC_ID));
  }

  private boolean isUpdatedNameNotUnique(String requestName, String currentName) {
    return isCollectionNameAlreadyExists(requestName) && !requestName.equals(currentName);
  }

  @Override
  @Transactional
  public void deleteCollection(Long id) {
    var user = authenticationService.getCurrentUser();
    var collection = getCollectionById(id);
    var theme = collection.getTheme();
    theme.getCollections().remove(collection);
    cloudinaryService.removeByPublicId(collection.getImagePublicId());
    collection
        .getItems()
        .forEach(item -> cloudinaryService.removeByPublicId(item.getImagePublicId()));
    var owner = collection.getOwner();
    if (authenticationService.isPermissible(user, owner)) {
      repository.deleteById(id);
      themeService.removeIfNoChild(theme);
    }
  }

  @Override
  @Transactional
  public void addItemToCollection(String collectionName, ItemRequest request) {
    var user = authenticationService.getCurrentUser();
    var collection = getCollectionByName(collectionName);
    var owner = collection.getOwner();
    if (itemService.exists(request.getName())) {
      throw new ItemNameAlreadyExistException();
    }
    if (authenticationService.isPermissible(user, owner)) {
      var item = parseCollectionItem(collection, request);
      collection.getItems().add(item);
      repository.save(collection);
    }
  }

  @Override
  @Transactional
  public void updateCollectionItem(String collectionName, ItemRequest request) {
    var user = authenticationService.getCurrentUser();
    var collection = getCollectionByName(collectionName);
    var owner = collection.getOwner();
    var itemToUpdate = itemService.getItemById(request.getId());
    if (isUpdatedItemNameAlreadyExist(request.getName(), itemToUpdate.getName())) {
      throw new ItemNameAlreadyExistException();
    }
    if (authenticationService.isPermissible(user, owner)) {
      var tags = tagService.getTags(request.getTags());
      var fields = request.getFields();
      cloudinaryService.removeByPublicId(itemToUpdate.getImagePublicId());
      var cloudinaryResponse = cloudinaryService.sendToCloud(request.getImage().getBytes());
      itemToUpdate.setName(request.getName());
      itemToUpdate.setTags(tags);
      itemToUpdate.setFields(fields);
      itemToUpdate.setImageLink(cloudinaryResponse.get(CLOUDINARY_SECURE_URL));
      itemToUpdate.setImagePublicId(cloudinaryResponse.get(CLOUDINARY_PUBLIC_ID));
      itemService.update(itemToUpdate);
    }
  }

  private boolean isUpdatedItemNameAlreadyExist(String requestName, String currentName) {
    return itemService.exists(requestName) && !currentName.equals(requestName);
  }

  private CollectionItem parseCollectionItem(UserCollection collection, ItemRequest request) {
    var tags = tagService.getTags(request.getTags());
    var fields = request.getFields();
    var cloudinaryResponse = cloudinaryService.sendToCloud(request.getImage().getBytes());
    CollectionItem item = new CollectionItem();
    item.setName(request.getName());
    item.setTags(tags);
    item.setFields(fields);
    item.setImageLink(cloudinaryResponse.get(CLOUDINARY_SECURE_URL));
    item.setImagePublicId(cloudinaryResponse.get(CLOUDINARY_PUBLIC_ID));
    item.setCollection(collection);
    itemService.add(item);
    return item;
  }

  @Override
  @Transactional
  public void deleteItemFromCollection(String collectionName, Long itemId) {
    var user = authenticationService.getCurrentUser();
    var collection = getCollectionByName(collectionName);
    var owner = collection.getOwner();
    if (authenticationService.isPermissible(user, owner)) {
      var item = itemService.getItemById(itemId);
      cloudinaryService.removeByPublicId(item.getImagePublicId());
      itemService.delete(itemId);
    }
  }

  @Override
  @Transactional
  public void likeItem(Long itemId) {
    var user = authenticationService.getCurrentUser();
    CollectionItem item = itemService.getItemById(itemId);
    var likes = item.getLikes();
    if (isAlreadyLiked(user, likes)) {
      Like likeToRemove = findLike(user, likes);
      likes.remove(likeToRemove);
      likeService.delete(Objects.requireNonNull(likeToRemove).getId());
    } else {
      Like like = new Like();
      like.setAuthor(user);
      like.setItem(item);
      likes.add(like);
    }
    itemService.update(item);
  }

  private boolean isAlreadyLiked(User user, List<Like> likes) {
    return likes.stream()
        .map(Like::getAuthor)
        .anyMatch(author -> author.getUsername().equals(user.getUsername()));
  }

  private Like findLike(User user, List<Like> likes) {
    return likes.stream()
        .filter(like -> like.getAuthor().getUsername().equals(user.getUsername()))
        .findFirst()
        .orElseThrow(LikeNotFoundException::new);
  }

  @Override
  @Transactional
  public CollectionItemDto getCollectionItem(String name, Long itemId) {
    var collection = getCollectionByName(name);
    return itemService.getItemByCollectionAndId(collection, itemId);
  }

  @Override
  @Transactional
  public List<UserCollectionDto> getTopUserCollections(Long count) {
    var top =
        repository.findAll().stream()
            .sorted((first, second) -> second.getItems().size() - first.getItems().size())
            .limit(count)
            .toList();
    return mapper.userCollectionsToUserCollectionDtos(top);
  }

  @Override
  @Transactional
  public ResponseEntity<ServerResponse> getUserStatistics() {
    var user = authenticationService.getCurrentUser();
    int collectionsCount = repository.countByOwner(user);
    int likesCount = likeService.countByAuthor(user);
    int commentsCount = commentService.countByAuthor(user);
    ServerResponse response = new StatisticsResponse(collectionsCount, likesCount, commentsCount);
    return ResponseEntity.ok(response);
  }
}
