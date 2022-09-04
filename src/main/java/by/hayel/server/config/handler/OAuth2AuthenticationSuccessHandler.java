package by.hayel.server.config.handler;

import by.hayel.server.config.property.OAuth2Property;
import by.hayel.server.model.security.UserPrincipal;
import by.hayel.server.service.security.JwtService;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  JwtService jwtService;
  OAuth2Property property;

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException {
    handle(request, response, authentication);
    super.clearAuthenticationAttributes(request);
  }

  @Override
  protected void handle(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException {
    var principal = (UserPrincipal) authentication.getPrincipal();
    String targetUrl = property.getRedirect();
    if (principal != null) {
      String token = jwtService.generateTokenFromUsername(principal.getUsername());
      targetUrl = buildTargetUrl(targetUrl, token);
      getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
  }

  @NonNull
  private String buildTargetUrl(String targetUrl, String token) {
    return UriComponentsBuilder.fromUriString(targetUrl)
        .queryParam(property.getQueryParameter(), token)
        .build()
        .toUriString();
  }
}
