package by.hayel.server.web.payload.response;

import by.hayel.server.web.payload.ServerResponse;
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
public class TokenRefreshResponse implements ServerResponse {
  public static final String DEFAULT_TOKEN_TYPE = "Bearer";

  @NonNull String accessToken;
  @NonNull String refreshToken;
  String tokenType = DEFAULT_TOKEN_TYPE;
}
