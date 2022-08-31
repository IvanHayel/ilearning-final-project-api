package by.hayel.server.model.entity.collection;

import by.hayel.server.model.entity.BaseEntity;
import java.io.Serial;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;

@Entity
@Table(name = "themes")
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Theme extends BaseEntity {
  @Serial private static final long serialVersionUID = 1L;

  @FullTextField
  @Column(name = "theme_name", nullable = false, unique = true)
  String name;

  @OneToMany(mappedBy = "theme", orphanRemoval = true)
  Set<UserCollection> collections = new HashSet<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Theme theme = (Theme) o;
    return id != null && Objects.equals(id, theme.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
