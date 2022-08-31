package by.hayel.server.web.payload.response;

import by.hayel.server.web.payload.ServerResponse;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtResponse implements ServerResponse {
  public static final String DEFAULT_TOKEN_TYPE = "Bearer";

  @NonNull String token;
  String tokenType = DEFAULT_TOKEN_TYPE;
  @NonNull String refreshToken;
  @NonNull Long id;
  @NonNull String username;
  @NonNull String email;
  @NonNull Set<String> roles;
}
