package by.hayel.server.web.controller.rest;

import by.hayel.server.model.entity.user.dto.UserDto;
import by.hayel.server.model.message.LocaleMessage;
import by.hayel.server.service.message.MessageService;
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
  MessageService messageService;

  @GetMapping
  public List<UserDto> getAllUsers() {
    return userService.getAllUsers();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ServerResponse> deleteUser(@PathVariable Long id, Locale locale) {
    ServerResponse message;
    if (userService.isRoot(id)) {
      message = messageService.buildMessageResponse(LocaleMessage.ERROR_DELETE_ROOT, locale);
      return ResponseEntity.badRequest().body(message);
    }
    userService.deleteById(id);
    message = messageService.buildMessageResponse(LocaleMessage.SUCCESS_DELETE_USER, locale);
    return ResponseEntity.ok(message);
  }

  @PutMapping("/block/{id}")
  public ResponseEntity<ServerResponse> blockUser(@PathVariable Long id, Locale locale) {
    ServerResponse message;
    if (userService.isRoot(id)) {
      message = messageService.buildMessageResponse(LocaleMessage.ERROR_BLOCK_ROOT, locale);
      return ResponseEntity.badRequest().body(message);
    }
    userService.blockById(id);
    message = messageService.buildMessageResponse(LocaleMessage.SUCCESS_BLOCK_USER, locale);
    return ResponseEntity.ok(message);
  }

  @PutMapping("/unblock/{id}")
  public ResponseEntity<ServerResponse> unblockUser(@PathVariable Long id, Locale locale) {
    userService.unblockById(id);
    var message = messageService.buildMessageResponse(LocaleMessage.SUCCESS_UNBLOCK_USER, locale);
    return ResponseEntity.ok(message);
  }

  @PutMapping("/grant-admin-rights/{id}")
  public ResponseEntity<ServerResponse> grantAdminRights(@PathVariable Long id, Locale locale) {
    userService.grantAdminRights(id);
    var message =
        messageService.buildMessageResponse(LocaleMessage.SUCCESS_GRANT_ADMIN_RIGHTS, locale);
    return ResponseEntity.ok(message);
  }

  @PutMapping("/revoke-admin-rights/{id}")
  public ResponseEntity<ServerResponse> revokeAdminRights(@PathVariable Long id, Locale locale) {
    userService.revokeAdminRights(id);
    var message =
        messageService.buildMessageResponse(LocaleMessage.SUCCESS_REVOKE_ADMIN_RIGHTS, locale);
    return ResponseEntity.ok(message);
  }

  @GetMapping("/verify-token")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'ROOT')")
  public HttpStatus verifyToken() {
    return HttpStatus.OK;
  }
}
