package by.hayel.server.web.filter.security;

import by.hayel.server.exception.security.BlockedUserException;
import by.hayel.server.service.security.JwtService;
import by.hayel.server.service.user.UserService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationTokenFilter extends OncePerRequestFilter {
  private static final String AUTHENTICATION_PATTERN = "^/api/auth.*";

  private static final String AUTHENTICATE_WARN_LOG = "Unable to authenticate user -> {}";

  UserService userService;
  JwtService jwtService;
  UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String requestUri = request.getRequestURI();
      boolean isAuthenticationRequest = requestUri.matches(AUTHENTICATION_PATTERN);
      if (isAuthenticationRequest) {
        filterChain.doFilter(request, response);
        return;
      }
      String token = jwtService.getTokenFromRequest(request);
      if (token != null && jwtService.isTokenValid(token)) {
        String username = jwtService.getUsernameFromToken(token);
        if (userService.isBlocked(username)) throw new BlockedUserException();
        allow(request, username);
      }
    } catch (Exception e) {
      log.debug(AUTHENTICATE_WARN_LOG, e.getMessage());
    }
    filterChain.doFilter(request, response);
  }

  private void allow(HttpServletRequest request, String username) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    var authorities = userDetails.getAuthorities();
    var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    var authenticationDetails = new WebAuthenticationDetailsSource().buildDetails(request);
    authentication.setDetails(authenticationDetails);
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }
}
