package by.hayel.server.exception.cloud;

import java.io.Serial;

public class CloudinaryUploadException extends RuntimeException {
  @Serial private static final long serialVersionUID = 1L;

  private static final String DEFAULT_MESSAGE = "Cloudinary upload exception!";

  public CloudinaryUploadException() {
    super(DEFAULT_MESSAGE);
  }
}
