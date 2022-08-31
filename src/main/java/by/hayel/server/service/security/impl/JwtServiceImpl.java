package by.hayel.server.service.security.impl;

import by.hayel.server.exception.security.HttpRequestTokenNotFoundException;
import by.hayel.server.model.entity.token.RefreshToken;
import by.hayel.server.model.entity.user.User;
import by.hayel.server.model.security.UserPrincipal;
import by.hayel.server.service.security.JwtService;
import by.hayel.server.service.security.RefreshTokenService;
import by.hayel.server.service.user.UserService;
import by.hayel.server.web.payload.response.JwtResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtServiceImpl implements JwtService {
  private static final String INVALID_JWT_SIGNATURE_LOG = "Invalid JWT signature -> {}";
  private static final String INVALID_JWT_TOKEN_LOG = "Invalid token -> {}";
  private static final String TOKEN_EXPIRED_LOG = "Token is expired -> {}";
  private static final String TOKEN_UNSUPPORTED_LOG = "Token is unsupported -> {}";
  private static final String CLAIMS_IS_EMPTY_LOG = "JWT claims string is empty -> {}";

  final RefreshTokenService refreshTokenService;
  final UserService userService;

  @Value("${jwt.secret}")
  String secret;

  @Value("${jwt.expiration-time}")
  long expirationTime;

  @Value("${jwt.header.name}")
  String headerName;

  @Value("${jwt.type}")
  String type;

  @Override
  public String generateToken(UserDetails userPrincipal) {
    return generateTokenFromUsername(userPrincipal.getUsername());
  }

  public String generateTokenFromUsername(String username) {
    Date now = new Date();
    ZonedDateTime expiryZonedDate =
        LocalDateTime.now().plus(expirationTime, ChronoUnit.MILLIS).atZone(ZoneId.systemDefault());
    Date expiryDate = Date.from(expiryZonedDate.toInstant());
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(now)
        .setExpiration(expiryDate)
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }

  public String getUsernameFromToken(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
  }

  @Override
  public String getTokenFromRequest(HttpServletRequest request) {
    String header = request.getHeader(headerName);
    if (StringUtils.hasText(header) && header.startsWith(type)) {
      return header.substring(type.length() + 1);
    }
    throw new HttpRequestTokenNotFoundException();
  }

  @Override
  public String getTokenFromSocketAccessor(String token) {
    if (StringUtils.hasText(token) && token.startsWith(type)) {
      return token.substring(type.length() + 1);
    }
    throw new HttpRequestTokenNotFoundException();
  }

  public boolean isTokenValid(String accessToken) {
    try {
      Jwts.parser().setSigningKey(secret).parseClaimsJws(accessToken);
      return true;
    } catch (SignatureException e) {
      log.error(INVALID_JWT_SIGNATURE_LOG, e.getMessage());
    } catch (MalformedJwtException e) {
      log.error(INVALID_JWT_TOKEN_LOG, e.getMessage());
    } catch (ExpiredJwtException e) {
      log.error(TOKEN_EXPIRED_LOG, e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.error(TOKEN_UNSUPPORTED_LOG, e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error(CLAIMS_IS_EMPTY_LOG, e.getMessage());
    }
    return false;
  }

  @Override
  public JwtResponse buildJwtResponse(UserPrincipal principal) {
    String accessToken = generateToken(principal);
    User user = userService.getUserById(principal.getId());
    RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
    var roles =
        user.getRoles().stream().map(role -> role.getName().toString()).collect(Collectors.toSet());
    userService.registerSignIn(principal.getId());
    return new JwtResponse(
        accessToken,
        refreshToken.getToken(),
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        roles);
  }
}
