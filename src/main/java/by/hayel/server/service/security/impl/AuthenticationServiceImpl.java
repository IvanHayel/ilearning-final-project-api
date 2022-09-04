package by.hayel.server.service.security.impl;

import by.hayel.server.exception.security.AccessDeniedException;
import by.hayel.server.exception.security.BlockedUserException;
import by.hayel.server.model.entity.token.RefreshToken;
import by.hayel.server.model.entity.user.User;
import by.hayel.server.model.message.LocaleMessage;
import by.hayel.server.model.security.UserPrincipal;
import by.hayel.server.service.message.MessageService;
import by.hayel.server.service.security.AuthenticationService;
import by.hayel.server.service.security.JwtService;
import by.hayel.server.service.security.RefreshTokenService;
import by.hayel.server.service.user.UserService;
import by.hayel.server.web.payload.ServerResponse;
import by.hayel.server.web.payload.request.SignInRequest;
import by.hayel.server.web.payload.request.SignUpRequest;
import by.hayel.server.web.payload.request.TokenRefreshRequest;
import by.hayel.server.web.payload.response.TokenRefreshResponse;
import java.util.Locale;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements AuthenticationService {
  AuthenticationManager authenticationManager;
  PasswordEncoder encoder;

  JwtService jwtService;
  RefreshTokenService refreshTokenService;
  UserService userService;
  MessageService messageService;

  @Override
  public ResponseEntity<ServerResponse> authenticate(SignInRequest request) {
    var authenticationToken =
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
    Authentication authentication = authenticationManager.authenticate(authenticationToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
    ServerResponse response = jwtService.buildJwtResponse(principal);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<ServerResponse> authenticateOAuth2(String request) {
    String token = request.substring(1, request.length() - 1);
    if (jwtService.isTokenValid(token)) {
      String tokenOwnerUsername = jwtService.getUsernameFromToken(token);
      Optional<User> optionalUser = userService.getUserByUsername(tokenOwnerUsername);
      if (optionalUser.isPresent()) {
        UserPrincipal principal = UserPrincipal.build(optionalUser.get());
        ServerResponse response = jwtService.buildJwtResponse(principal);
        return ResponseEntity.ok(response);
      }
    }
    throw new AccessDeniedException();
  }

  @Override
  public ResponseEntity<ServerResponse> register(SignUpRequest request, Locale locale) {
    ServerResponse message;
    if (userService.isUsernameAlreadyExist(request.getUsername())) {
      message = messageService.buildMessageResponse(LocaleMessage.ERROR_USERNAME_TAKEN, locale);
      return ResponseEntity.badRequest().body(message);
    }
    if (userService.isEmailAlreadyExist(request.getEmail())) {
      message = messageService.buildMessageResponse(LocaleMessage.ERROR_EMAIL_TAKEN, locale);
      return ResponseEntity.badRequest().body(message);
    }
    User user = parseUser(request);
    userService.saveNewUser(user);
    message = messageService.buildMessageResponse(LocaleMessage.SUCCESS_REGISTRATION, locale);
    return ResponseEntity.ok(message);
  }

  private User parseUser(SignUpRequest signUpRequest) {
    User user = new User();
    user.setUsername(signUpRequest.getUsername());
    user.setEmail(signUpRequest.getEmail());
    user.setPassword(encoder.encode(signUpRequest.getPassword()));
    return user;
  }

  @Override
  public ResponseEntity<ServerResponse> logout(Long id, Locale locale) {
    userService.registerSignOut(id);
    var message = messageService.buildMessageResponse(LocaleMessage.SUCCESS_LOGOUT, locale);
    return ResponseEntity.ok(message);
  }

  @Override
  public ResponseEntity<ServerResponse> refreshToken(TokenRefreshRequest request) {
    String requestRefreshToken = request.getRefreshToken();
    RefreshToken refreshToken = refreshTokenService.getByToken(requestRefreshToken);
    return getResponseIfAllowed(refreshToken);
  }

  private ResponseEntity<ServerResponse> getResponseIfAllowed(RefreshToken refreshToken) {
    RefreshToken verified = refreshTokenService.verifyExpiration(refreshToken);
    String tokenOwnerUsername = verified.getUser().getUsername();
    if (userService.isBlocked(tokenOwnerUsername)) throw new BlockedUserException();
    String newToken = jwtService.generateTokenFromUsername(tokenOwnerUsername);
    ServerResponse response = new TokenRefreshResponse(newToken, verified.getToken());
    return ResponseEntity.ok(response);
  }

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
