package by.hayel.server.service.security;

import by.hayel.server.model.entity.user.User;
import by.hayel.server.web.payload.ServerResponse;
import by.hayel.server.web.payload.request.SignInRequest;
import by.hayel.server.web.payload.request.SignUpRequest;
import by.hayel.server.web.payload.request.TokenRefreshRequest;
import java.util.Locale;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
  ResponseEntity<ServerResponse> authenticate(SignInRequest request);

  ResponseEntity<ServerResponse> authenticateOAuth2(String request);

  ResponseEntity<ServerResponse> register(SignUpRequest request, Locale locale);

  ResponseEntity<ServerResponse> logout(Long id, Locale locale);

  ResponseEntity<ServerResponse> refreshToken(TokenRefreshRequest request);

  User getCurrentUser();

  boolean isPermissible(User user, User resourceOwner);
}
