package by.hayel.server.repository.collection;

import by.hayel.server.model.entity.collection.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {
  Theme findByName(String name);

  boolean existsByName(String name);
}
