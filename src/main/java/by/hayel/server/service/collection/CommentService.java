package by.hayel.server.service.collection;

import by.hayel.server.model.entity.collection.CollectionItem;
import by.hayel.server.model.entity.collection.Comment;
import by.hayel.server.model.entity.collection.dto.CommentDto;
import by.hayel.server.model.entity.user.User;

public interface CommentService {
  Comment save(CommentDto commentDto, CollectionItem item);

  int countByAuthor(User user);
}
