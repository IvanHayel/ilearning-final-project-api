package by.hayel.server.exception.security;

import java.io.Serial;

public class RefreshTokenNotFoundException extends RuntimeException {
  @Serial private static final long serialVersionUID = 1L;

  private static final String DEFAULT_MESSAGE = "Refresh token not found!";

  public RefreshTokenNotFoundException() {
    super(DEFAULT_MESSAGE);
  }
}
