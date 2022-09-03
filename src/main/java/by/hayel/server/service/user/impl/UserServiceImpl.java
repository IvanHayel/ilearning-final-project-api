package by.hayel.server.service.user.impl;

import by.hayel.server.exception.user.UserNotFoundException;
import by.hayel.server.mapper.user.UserMapper;
import by.hayel.server.model.entity.user.Role;
import by.hayel.server.model.entity.user.RoleName;
import by.hayel.server.model.entity.user.User;
import by.hayel.server.model.entity.user.dto.UserDto;
import by.hayel.server.repository.user.UserRepository;
import by.hayel.server.service.collection.LikeService;
import by.hayel.server.service.security.RefreshTokenService;
import by.hayel.server.service.user.RoleService;
import by.hayel.server.service.user.UserService;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
  UserRepository repository;
  UserMapper mapper;
  RoleService roleService;
  LikeService likeService;
  RefreshTokenService refreshTokenService;

  @Override
  public List<UserDto> getAllUsers() {
    List<User> users = repository.findAll();
    return mapper.usersToUserDtos(users);
  }

  @Override
  public User getUserById(Long id) {
    return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
  }

  @Override
  public Optional<User> getUserByUsername(String username) {
    return repository.findByUsername(username);
  }

  @Override
  public Optional<User> getUserByEmail(String email) {
    return repository.findByEmail(email);
  }

  @Override
  public boolean isUsernameAlreadyExist(String username) {
    return repository.existsByUsername(username);
  }

  @Override
  public boolean isEmailAlreadyExist(String email) {
    return repository.existsByEmail(email);
  }

  @Override
  public boolean isBlocked(String username) {
    return repository.findByUsername(username).map(User::isBlocked).orElse(false);
  }

  @Override
  public boolean isRoot(Long id) {
    return repository.findById(id).orElseThrow(UserNotFoundException::new).getRoles().stream()
        .anyMatch(role -> role.getName().equals(RoleName.ROLE_ROOT));
  }

  @Override
  public boolean isAdmin(Long id) {
    return repository.findById(id).orElseThrow(UserNotFoundException::new).getRoles().stream()
        .anyMatch(role -> role.getName().equals(RoleName.ROLE_ADMIN));
  }

  @Override
  public void save(User user) {
    repository.save(user);
  }

  @Override
  public void saveNewUser(User user) {
    Role basicRole = roleService.getByName(RoleName.ROLE_USER);
    user.setRoles(Set.of(basicRole));
    repository.save(user);
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    likeService.deleteAllByAuthor(getUserById(id));
    repository.deleteById(id);
  }

  @Override
  public void registerSignIn(Long id) {
    User user = getUserById(id);
    user.setLastLogin(ZonedDateTime.now());
    user.setActive(true);
    repository.save(user);
  }

  @Override
  @Transactional
  public void registerSignOut(Long id) {
    User user = getUserById(id);
    user.setActive(false);
    repository.save(user);
    refreshTokenService.deleteAllByUser(user);
  }

  @Override
  @Transactional
  public void blockById(Long id) {
    User user = getUserById(id);
    user.setBlocked(true);
    user.setActive(false);
    repository.save(user);
    refreshTokenService.deleteAllByUser(user);
  }

  @Override
  @Transactional
  public void unblockById(Long id) {
    User user = getUserById(id);
    user.setBlocked(false);
    repository.save(user);
    refreshTokenService.deleteAllByUser(user);
  }

  @Override
  @Transactional
  public void grantAdminRights(Long id) {
    User user = getUserById(id);
    Set<Role> roles = user.getRoles();
    Role adminRole = roleService.getByName(RoleName.ROLE_ADMIN);
    roles.add(adminRole);
    user.setRoles(roles);
    repository.save(user);
    refreshTokenService.deleteAllByUser(user);
  }

  @Override
  @Transactional
  public void revokeAdminRights(Long id) {
    User user = getUserById(id);
    Set<Role> roles = user.getRoles();
    roles.removeIf(role -> role.getName().equals(RoleName.ROLE_ADMIN));
    user.setRoles(roles);
    repository.save(user);
    refreshTokenService.deleteAllByUser(user);
  }
}
