package by.hayel.server.service.security;

import by.hayel.server.model.entity.user.User;

public interface AuthenticationService {
  User getCurrentUser();

  boolean isPermissible(User user, User resourceOwner);
}
