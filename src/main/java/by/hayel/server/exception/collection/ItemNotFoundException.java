package by.hayel.server.exception.collection;

import java.io.Serial;

public class ItemNotFoundException extends RuntimeException {
  @Serial private static final long serialVersionUID = 1L;

  private static final String DEFAULT_MESSAGE = "Item not found!";

  public ItemNotFoundException() {
    super(DEFAULT_MESSAGE);
  }
}
