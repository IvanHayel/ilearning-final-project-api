package by.hayel.server.repository.collection;

import by.hayel.server.model.entity.collection.UserCollection;
import by.hayel.server.model.entity.user.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCollectionRepository extends JpaRepository<UserCollection, Long> {
  List<UserCollection> findAllByOwner(User owner, Sort sort);

  Optional<UserCollection> findByName(String name);

  int countByOwner(User owner);

  boolean existsByName(String name);
}
