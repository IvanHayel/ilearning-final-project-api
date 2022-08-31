package by.hayel.server.exception.collection;

import java.io.Serial;

public class CollectionNameAlreadyExistException extends RuntimeException {
  @Serial private static final long serialVersionUID = 1L;

  private static final String DEFAULT_MESSAGE = "Collection name already exist!";

  public CollectionNameAlreadyExistException() {
    super(DEFAULT_MESSAGE);
  }
}
