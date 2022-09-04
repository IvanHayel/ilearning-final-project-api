package by.hayel.server.service.collection;

import by.hayel.server.model.entity.user.User;

public interface LikeService {
  void delete(Long id);

  int countByAuthor(User user);
}
