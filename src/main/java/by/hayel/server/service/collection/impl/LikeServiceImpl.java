package by.hayel.server.service.collection.impl;

import by.hayel.server.model.entity.user.User;
import by.hayel.server.repository.collection.LikeRepository;
import by.hayel.server.service.collection.LikeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LikeServiceImpl implements LikeService {
  LikeRepository repository;

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }

  @Override
  @Transactional
  public void deleteAllByAuthor(User user) {
    repository.deleteAllByAuthor(user);
  }
}
