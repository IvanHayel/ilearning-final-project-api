package by.hayel.server.web.controller.rest;

import by.hayel.server.service.security.AuthenticationService;
import by.hayel.server.web.payload.ServerResponse;
import by.hayel.server.web.payload.request.SignInRequest;
import by.hayel.server.web.payload.request.SignUpRequest;
import by.hayel.server.web.payload.request.TokenRefreshRequest;
import java.util.Locale;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
  AuthenticationService authenticationService;

  @PostMapping("/sign-in")
  public ResponseEntity<ServerResponse> authenticateUser(
      @Valid @RequestBody SignInRequest request) {
    return authenticationService.authenticate(request);
  }

  @PostMapping("/oauth2-sign-in")
  public ResponseEntity<ServerResponse> authenticateUserOAuth2(
      @NotBlank @RequestBody String request) {
    return authenticationService.authenticateOAuth2(request);
  }

  @PostMapping("/sign-up")
  public ResponseEntity<ServerResponse> registerUser(
      @Valid @RequestBody SignUpRequest request, Locale locale) {
    return authenticationService.register(request, locale);
  }

  @PatchMapping("/sign-out/{id}")
  public ResponseEntity<ServerResponse> logoutUser(@PathVariable Long id, Locale locale) {
    return authenticationService.logout(id, locale);
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<ServerResponse> refreshToken(
      @Valid @RequestBody TokenRefreshRequest request) {
    return authenticationService.refreshToken(request);
  }
}
