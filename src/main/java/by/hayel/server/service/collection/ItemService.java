package by.hayel.server.service.collection;

import by.hayel.server.model.entity.collection.CollectionItem;
import by.hayel.server.model.entity.collection.dto.CommentDto;
import java.util.List;

public interface ItemService {
  List<CollectionItem> getItems();

  CollectionItem get(Long id);

  List<CommentDto> getItemComments(Long id);

  String getItemOwner(Long id);

  boolean exists(String name);

  boolean exists(Long id);

  void add(CollectionItem item);

  void update(CollectionItem item);

  void delete(Long id);

  CommentDto addComment(Long id, CommentDto comment);
}
