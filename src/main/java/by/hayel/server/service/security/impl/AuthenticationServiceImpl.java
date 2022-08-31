package by.hayel.server.service.security.impl;

import by.hayel.server.exception.security.AccessDeniedException;
import by.hayel.server.model.entity.user.User;
import by.hayel.server.model.security.UserPrincipal;
import by.hayel.server.service.security.AuthenticationService;
import by.hayel.server.service.user.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements AuthenticationService {
  UserService userService;

  @Override
  public User getCurrentUser() {
    try {
      var authentication = SecurityContextHolder.getContext().getAuthentication();
      var principal = (UserPrincipal) authentication.getPrincipal();
      return userService.getUserById(principal.getId());
    } catch (Exception e) {
      throw new AccessDeniedException();
    }
  }

  @Override
  public boolean isPermissible(User user, User resourceOwner) {
    return resourceOwner.getUsername().equals(user.getUsername())
        || userService.isAdmin(user.getId())
        || userService.isRoot(user.getId());
  }
}
