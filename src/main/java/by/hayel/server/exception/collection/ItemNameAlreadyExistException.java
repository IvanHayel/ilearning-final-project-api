package by.hayel.server.exception.collection;

import java.io.Serial;

public class ItemNameAlreadyExistException extends RuntimeException {
  @Serial private static final long serialVersionUID = 1L;

  private static final String DEFAULT_MESSAGE = "Collection name already exist!";

  public ItemNameAlreadyExistException() {
    super(DEFAULT_MESSAGE);
  }
}
