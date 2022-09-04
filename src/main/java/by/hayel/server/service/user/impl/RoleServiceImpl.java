package by.hayel.server.service.user.impl;

import by.hayel.server.exception.user.RoleNotFoundException;
import by.hayel.server.model.entity.user.Role;
import by.hayel.server.model.entity.user.RoleName;
import by.hayel.server.repository.user.RoleRepository;
import by.hayel.server.service.user.RoleService;
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
}
