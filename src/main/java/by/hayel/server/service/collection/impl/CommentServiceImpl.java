package by.hayel.server.service.collection.impl;

import by.hayel.server.mapper.collection.CommentMapper;
import by.hayel.server.model.entity.collection.CollectionItem;
import by.hayel.server.model.entity.collection.Comment;
import by.hayel.server.model.entity.collection.dto.CommentDto;
import by.hayel.server.repository.collection.CommentRepository;
import by.hayel.server.service.collection.CommentService;
import by.hayel.server.service.security.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentServiceImpl implements CommentService {
  CommentRepository repository;
  CommentMapper mapper;

  AuthenticationService authenticationService;

  @Override
  @Transactional
  public Comment save(CommentDto commentDto, CollectionItem item) {
    var user = authenticationService.getCurrentUser();
    var comment = mapper.commentDtoToComment(commentDto);
    comment.setItem(item);
    comment.setAuthor(user);
    return repository.save(comment);
  }
}
