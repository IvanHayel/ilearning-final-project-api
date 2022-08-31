package by.hayel.server.service.user;

import by.hayel.server.model.entity.user.Role;
import by.hayel.server.model.entity.user.RoleName;
import java.util.Set;

public interface RoleService {
  Role getByName(RoleName name);

  Role parseRole(String role);

  Set<Role> parseRoles(Set<String> roles);
}
