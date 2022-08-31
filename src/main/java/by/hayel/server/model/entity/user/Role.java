package by.hayel.server.model.entity.user;

import by.hayel.server.model.entity.BaseEntity;
import java.io.Serial;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role extends BaseEntity {
  @Serial private static final long serialVersionUID = 1L;

  @Enumerated(EnumType.STRING)
  @Column(name = "role_name", updatable = false, nullable = false, unique = true)
  RoleName name;
}
