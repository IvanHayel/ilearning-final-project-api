package by.hayel.server.web.controller.rest;

import by.hayel.server.model.entity.user.dto.UserDto;
import by.hayel.server.service.user.UserService;
import by.hayel.server.web.payload.ServerResponse;
import java.util.List;
import java.util.Locale;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasAnyRole('ADMIN', 'ROOT')")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
  UserService userService;

  @GetMapping
  public List<UserDto> getAllUsers() {
    return userService.getAllUsers();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ServerResponse> deleteUser(@PathVariable Long id, Locale locale) {
    return userService.deleteUser(id, locale);
  }

  @PutMapping("/block/{id}")
  public ResponseEntity<ServerResponse> blockUser(@PathVariable Long id, Locale locale) {
    return userService.blockUser(id, locale);
  }

  @PutMapping("/unblock/{id}")
  public ResponseEntity<ServerResponse> unblockUser(@PathVariable Long id, Locale locale) {
    return userService.unblockUser(id, locale);
  }

  @PutMapping("/grant-admin-rights/{id}")
  public ResponseEntity<ServerResponse> grantAdminRights(@PathVariable Long id, Locale locale) {
    return userService.grantUserAdminRights(id, locale);
  }

  @PutMapping("/revoke-admin-rights/{id}")
  public ResponseEntity<ServerResponse> revokeAdminRights(@PathVariable Long id, Locale locale) {
    return userService.revokeUserAdminRights(id, locale);
  }

  @GetMapping("/verify-token")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'ROOT')")
  public HttpStatus verifyToken() {
    return HttpStatus.OK;
  }
}
