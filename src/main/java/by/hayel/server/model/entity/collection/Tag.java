package by.hayel.server.model.entity.collection;

import by.hayel.server.model.entity.BaseEntity;
import java.io.Serial;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;

@Entity
@Table(name = "tags")
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Tag extends BaseEntity {
  @Serial private static final long serialVersionUID = 1L;

  @FullTextField
  @Column(name = "tag_name", nullable = false, unique = true)
  String name;

  @ManyToMany(
      mappedBy = "tags",
      cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  Set<CollectionItem> items = new HashSet<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Tag tag = (Tag) o;
    return id != null && Objects.equals(id, tag.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
