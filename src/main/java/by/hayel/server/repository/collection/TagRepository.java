package by.hayel.server.repository.collection;

import by.hayel.server.model.entity.collection.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
  Tag findByName(String name);

  boolean existsByName(String name);
}
