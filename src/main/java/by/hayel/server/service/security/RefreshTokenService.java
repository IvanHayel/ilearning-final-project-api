package by.hayel.server.service.security;

import by.hayel.server.model.entity.token.RefreshToken;
import by.hayel.server.model.entity.user.User;

public interface RefreshTokenService {
  RefreshToken getByToken(String token);

  RefreshToken createRefreshToken(User user);

  RefreshToken verifyExpiration(RefreshToken token);

  void deleteAllByUser(User user);
}
