package by.hayel.server.model.entity.user;

import by.hayel.server.model.entity.BaseEntity;
import java.io.Serial;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@NamedEntityGraph(
    name = "graph.User.withRoles",
    attributeNodes = @NamedAttributeNode(value = "roles"))
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity {
  @Serial private static final long serialVersionUID = 1L;

  @Column(nullable = false, unique = true)
  String username;

  @Column(name = "secret", nullable = false)
  String password;

  @ManyToMany
  @JoinTable(
      name = "user_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  Set<Role> roles = new HashSet<>();

  @Column(nullable = false, unique = true)
  String email;

  @Column(nullable = false)
  ZonedDateTime lastLogin;

  @Column(nullable = false)
  boolean active;

  @Column(nullable = false)
  boolean blocked;

  @PrePersist
  void prePersist() {
    lastLogin = ZonedDateTime.now();
    active = false;
    blocked = false;
  }
}
