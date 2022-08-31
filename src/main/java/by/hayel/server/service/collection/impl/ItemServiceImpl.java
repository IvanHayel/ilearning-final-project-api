package by.hayel.server.service.collection.impl;

import by.hayel.server.mapper.collection.CommentMapper;
import by.hayel.server.model.entity.collection.CollectionItem;
import by.hayel.server.model.entity.collection.dto.CommentDto;
import by.hayel.server.repository.collection.CollectionItemRepository;
import by.hayel.server.service.collection.CommentService;
import by.hayel.server.service.collection.ItemService;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ItemServiceImpl implements ItemService {
  CollectionItemRepository repository;

  CommentService commentService;
  CommentMapper commentMapper;

  @Override
  public List<CollectionItem> getItems() {
    return repository.findAll();
  }

  @Override
  @Transactional
  public CollectionItem get(Long id) {
    return repository.findById(id).orElse(null);
  }

  @Override
  @Transactional
  public List<CommentDto> getItemComments(Long id) {
    var item = get(id);
    var comments = item.getComments();
    return commentMapper.commentsToCommentDtos(comments);
  }

  @Override
  @Transactional
  public String getItemOwner(Long id) {
    var item = get(id);
    return item.getCollection().getOwner().getUsername();
  }

  @Override
  @Transactional
  public boolean exists(String name) {
    return repository.existsByName(name);
  }

  @Override
  public boolean exists(Long id) {
    return repository.existsById(id);
  }

  @Override
  @Transactional
  public void add(CollectionItem item) {
    if (!exists(item.getName())) {
      repository.save(item);
    }
  }

  @Override
  @Transactional
  public void update(CollectionItem item) {
    if (exists(item.getName())) {
      repository.save(item);
    }
  }

  @Override
  @Transactional
  public void delete(Long id) {
    if (exists(id)) {
      repository.deleteById(id);
    }
  }

  @Override
  @Transactional
  public CommentDto addComment(Long id, CommentDto commentDto) {
    var item = get(id);
    var comment = commentService.save(commentDto, item);
    item.getComments().add(comment);
    update(item);
    return commentMapper.commentToCommentDto(comment);
  }
}
