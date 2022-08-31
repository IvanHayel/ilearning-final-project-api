package by.hayel.server.service.collection;

import by.hayel.server.model.entity.collection.CollectionItem;
import by.hayel.server.model.entity.collection.Comment;
import by.hayel.server.model.entity.collection.dto.CommentDto;

public interface CommentService {
  Comment save(CommentDto commentDto, CollectionItem item);
}
