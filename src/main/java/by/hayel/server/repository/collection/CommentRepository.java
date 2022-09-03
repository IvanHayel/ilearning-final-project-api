package by.hayel.server.repository.collection;

import by.hayel.server.model.entity.collection.Comment;
import by.hayel.server.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
  int countByAuthor(User author);
}
