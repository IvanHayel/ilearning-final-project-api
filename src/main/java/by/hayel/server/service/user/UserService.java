package by.hayel.server.service.user;

import by.hayel.server.model.entity.user.User;
import by.hayel.server.model.entity.user.dto.UserDto;
import by.hayel.server.web.payload.ServerResponse;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

public interface UserService {
  List<UserDto> getAllUsers();

  User getUserById(Long id);

  Optional<User> getUserByUsername(String username);

  Optional<User> getUserByEmail(String email);

  boolean isUsernameAlreadyExist(String username);

  boolean isEmailAlreadyExist(String email);

  boolean isBlocked(String username);

  boolean isRoot(Long id);

  boolean isAdmin(Long id);

  void save(User user);

  void saveNewUser(User user);

  void deleteById(Long id);

  void registerSignIn(Long id);

  void registerSignOut(Long id);

  void blockById(Long id);

  void unblockById(Long id);

  void grantAdminRights(Long id);

  void revokeAdminRights(Long id);

  ResponseEntity<ServerResponse> deleteUser(Long id, Locale locale);

  ResponseEntity<ServerResponse> blockUser(Long id, Locale locale);

  ResponseEntity<ServerResponse> unblockUser(Long id, Locale locale);

  ResponseEntity<ServerResponse> grantUserAdminRights(Long id, Locale locale);

  ResponseEntity<ServerResponse> revokeUserAdminRights(Long id, Locale locale);
}
