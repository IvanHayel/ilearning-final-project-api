package by.hayel.server.repository.collection;

import by.hayel.server.model.entity.collection.Like;
import by.hayel.server.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
  void deleteAllByAuthor(User author);
}
