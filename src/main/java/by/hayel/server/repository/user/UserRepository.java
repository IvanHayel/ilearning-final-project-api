package by.hayel.server.repository.user;

import by.hayel.server.model.entity.user.User;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  @Override
  @NonNull
  @EntityGraph(value = "graph.User.withRoles")
  List<User> findAll();

  @Override
  @NonNull
  @EntityGraph(value = "graph.User.withRoles")
  Optional<User> findById(@NonNull Long id);

  @NonNull
  @EntityGraph(value = "graph.User.withRoles")
  Optional<User> findByUsername(String username);

  @NonNull
  @EntityGraph(value = "graph.User.withRoles")
  Optional<User> findByEmail(String email);

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);
}
