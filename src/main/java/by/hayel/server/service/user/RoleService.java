package by.hayel.server.service.user;

import by.hayel.server.model.entity.user.Role;
import by.hayel.server.model.entity.user.RoleName;

public interface RoleService {
  Role getByName(RoleName name);
}
