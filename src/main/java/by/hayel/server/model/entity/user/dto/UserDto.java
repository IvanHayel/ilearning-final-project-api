package by.hayel.server.model.entity.user.dto;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDto implements Serializable {
  @Serial private static final long serialVersionUID = 1L;

  Long id;
  ZonedDateTime createDate;
  ZonedDateTime updateDate;
  String username;
  Set<RoleDto> roles;
  String email;
  ZonedDateTime lastLogin;
  boolean active;
  boolean blocked;
}
