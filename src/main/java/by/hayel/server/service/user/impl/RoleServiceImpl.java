package by.hayel.server.service.user.impl;

import by.hayel.server.exception.user.RoleNotFoundException;
import by.hayel.server.model.entity.user.Role;
import by.hayel.server.model.entity.user.RoleName;
import by.hayel.server.repository.user.RoleRepository;
import by.hayel.server.service.user.RoleService;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements RoleService {
  private static final String ROLE_PREFIX = "ROLE_";

  RoleRepository repository;

  @Override
  public Role getByName(RoleName name) {
    return repository.findByName(name).orElseThrow(RoleNotFoundException::new);
  }

  @Override
  public Role parseRole(String role) {
    String modifiedRole = ROLE_PREFIX.concat(role.toUpperCase());
    try {
      return getByName(RoleName.valueOf(modifiedRole));
    } catch (IllegalArgumentException e) {
      return getByName(RoleName.ROLE_USER);
    }
  }

  @Override
  public Set<Role> parseRoles(Set<String> requestRoles) {
    Set<Role> roleSet = new HashSet<>();
    if (requestRoles == null) {
      Role user = getByName(RoleName.ROLE_USER);
      roleSet.add(user);
    } else {
      requestRoles.forEach(role -> roleSet.add(parseRole(role)));
    }
    return roleSet;
  }
}
