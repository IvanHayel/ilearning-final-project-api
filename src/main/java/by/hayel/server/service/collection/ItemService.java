package by.hayel.server.service.collection;

import by.hayel.server.model.entity.collection.CollectionItem;
import by.hayel.server.model.entity.collection.UserCollection;
import by.hayel.server.model.entity.collection.dto.CollectionItemDto;
import by.hayel.server.model.entity.collection.dto.CommentDto;
import java.util.List;
import org.springframework.data.domain.Sort;

public interface ItemService {
  List<CollectionItem> getItems();

  List<CollectionItemDto> getItemsByCollection(UserCollection collection, Sort sort);

  CollectionItemDto getItemByCollectionAndId(UserCollection collection, Long id);

  CollectionItem getItemById(Long id);

  List<CommentDto> getItemComments(Long id);

  String getItemOwner(Long id);

  boolean exists(String name);

  boolean exists(Long id);

  void add(CollectionItem item);

  void update(CollectionItem item);

  void delete(Long id);

  CommentDto addComment(Long id, CommentDto comment);
}
