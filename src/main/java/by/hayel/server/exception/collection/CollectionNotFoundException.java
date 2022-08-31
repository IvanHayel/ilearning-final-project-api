package by.hayel.server.exception.collection;

import java.io.Serial;

public class CollectionNotFoundException extends RuntimeException {
  @Serial private static final long serialVersionUID = 1L;

  private static final String DEFAULT_MESSAGE = "Collection not found exception!";

  public CollectionNotFoundException() {
    super(DEFAULT_MESSAGE);
  }
}
