package by.hayel.server.repository.collection;

import by.hayel.server.model.entity.collection.CollectionItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionItemRepository extends JpaRepository<CollectionItem, Long> {
  boolean existsByName(String name);
}
