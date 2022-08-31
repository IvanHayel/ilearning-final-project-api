package by.hayel.server.service.security.impl;

import by.hayel.server.exception.security.RefreshTokenNotFoundException;
import by.hayel.server.exception.security.TokenRefreshException;
import by.hayel.server.model.entity.token.RefreshToken;
import by.hayel.server.model.entity.user.User;
import by.hayel.server.repository.security.RefreshTokenRepository;
import by.hayel.server.service.security.RefreshTokenService;
import java.time.Instant;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshTokenServiceImpl implements RefreshTokenService {
  private static final String REFRESH_TOKEN_EXPIRED = "Refresh token has expired!";

  final RefreshTokenRepository repository;

  @Value("${jwt.refresh.expiration-time}")
  long refreshTokenDuration;

  @Override
  public RefreshToken getByToken(String token) {
    return repository.findByToken(token).orElseThrow(RefreshTokenNotFoundException::new);
  }

  @Override
  public RefreshToken createRefreshToken(User user) {
    RefreshToken refreshToken = new RefreshToken();
    String token = UUID.randomUUID().toString();
    Instant expiryDate = Instant.now().plusMillis(refreshTokenDuration);
    refreshToken.setUser(user);
    refreshToken.setToken(token);
    refreshToken.setExpiryDate(expiryDate);
    return repository.save(refreshToken);
  }

  @Override
  public RefreshToken verifyExpiration(RefreshToken token) {
    Instant expiryDate = token.getExpiryDate();
    Instant now = Instant.now();
    if (expiryDate.isBefore(now)) {
      repository.delete(token);
      throw new TokenRefreshException(token.getToken(), REFRESH_TOKEN_EXPIRED);
    }
    return token;
  }

  @Override
  public void deleteAllByUser(User user) {
    repository.deleteAllByUser(user);
  }
}
