package by.hayel.server.repository.collection;

import by.hayel.server.model.entity.collection.CollectionItem;
import by.hayel.server.model.entity.collection.UserCollection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionItemRepository extends JpaRepository<CollectionItem, Long> {
  List<CollectionItem> findAllByCollection(UserCollection collection, Sort sort);

  Optional<CollectionItem> findByCollectionAndId(UserCollection collection, Long id);

  boolean existsByName(String name);
}
