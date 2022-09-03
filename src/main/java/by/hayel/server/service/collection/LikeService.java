package by.hayel.server.service.collection;

import by.hayel.server.model.entity.user.User;

public interface LikeService {
  void delete(Long id);

  void deleteAllByAuthor(User user);

  int countByAuthor(User user);
}
