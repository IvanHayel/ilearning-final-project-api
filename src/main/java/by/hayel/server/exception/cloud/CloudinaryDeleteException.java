package by.hayel.server.exception.cloud;

import java.io.Serial;

public class CloudinaryDeleteException extends RuntimeException {
  @Serial private static final long serialVersionUID = 1L;

  private static final String DEFAULT_MESSAGE = "Cloudinary delete exception!";

  public CloudinaryDeleteException() {
    super(DEFAULT_MESSAGE);
  }
}
