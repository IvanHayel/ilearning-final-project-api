package by.hayel.server.exception.collection;

import java.io.Serial;

public class LikeNotFoundException extends RuntimeException {
  @Serial private static final long serialVersionUID = 1L;

  private static final String DEFAULT_MESSAGE = "Like not found!";

  public LikeNotFoundException() {
    super(DEFAULT_MESSAGE);
  }
}
