package by.hayel.server.service.collection.impl;

import by.hayel.server.exception.collection.ItemNotFoundException;
import by.hayel.server.mapper.collection.CollectionItemMapper;
import by.hayel.server.mapper.collection.CommentMapper;
import by.hayel.server.model.entity.collection.CollectionItem;
import by.hayel.server.model.entity.collection.UserCollection;
import by.hayel.server.model.entity.collection.dto.CollectionItemDto;
import by.hayel.server.model.entity.collection.dto.CommentDto;
import by.hayel.server.repository.collection.CollectionItemRepository;
import by.hayel.server.service.collection.CommentService;
import by.hayel.server.service.collection.ItemService;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ItemServiceImpl implements ItemService {
  CollectionItemRepository repository;
  CollectionItemMapper mapper;

  CommentService commentService;
  CommentMapper commentMapper;

  @Override
  public List<CollectionItem> getItems() {
    return repository.findAll();
  }

  @Override
  @Transactional
  public List<CollectionItemDto> getItemsByCollection(UserCollection collection, Sort sort) {
    var items = repository.findAllByCollection(collection, sort);
    return mapper.collectionItemsToCollectionItemDtos(items);
  }

  @Override
  @Transactional
  public CollectionItemDto getItemByCollectionAndId(UserCollection collection, Long id) {
    var item =
        repository.findByCollectionAndId(collection, id).orElseThrow(ItemNotFoundException::new);
    return mapper.collectionItemToCollectionItemDto(item);
  }

  @Override
  public CollectionItem getItemById(Long id) {
    return repository.findById(id).orElseThrow(ItemNotFoundException::new);
  }

  @Override
  @Transactional
  public List<CommentDto> getItemComments(Long id) {
    var item = getItemById(id);
    var comments = item.getComments();
    return commentMapper.commentsToCommentDtos(comments);
  }

  @Override
  @Transactional
  public String getItemOwner(Long id) {
    var item = getItemById(id);
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
    var item = getItemById(id);
    var comment = commentService.save(commentDto, item);
    item.getComments().add(comment);
    update(item);
    return commentMapper.commentToCommentDto(comment);
  }
}
