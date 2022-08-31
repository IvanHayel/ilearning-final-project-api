package by.hayel.server.repository.collection;

import by.hayel.server.model.entity.collection.ItemField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemFieldRepository extends JpaRepository<ItemField, Long> {}
