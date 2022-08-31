package by.hayel.server.service.security;

import by.hayel.server.model.security.UserPrincipal;
import by.hayel.server.web.payload.response.JwtResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
  String generateToken(UserDetails userDetails);

  String generateTokenFromUsername(String username);

  String getUsernameFromToken(String token);

  String getTokenFromRequest(HttpServletRequest request);

  String getTokenFromSocketAccessor(String token);

  JwtResponse buildJwtResponse(UserPrincipal principal);

  boolean isTokenValid(String token);
}
