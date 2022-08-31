package by.hayel.server.exception.user;

import java.io.Serial;

public class UserNotFoundException extends RuntimeException {
  @Serial private static final long serialVersionUID = 1L;

  private static final String DEFAULT_MESSAGE = "User not found!";
  private static final String MESSAGE_WITH_ID = "User with id %d not found!";

  public UserNotFoundException() {
    super(DEFAULT_MESSAGE);
  }

  public UserNotFoundException(Long id) {
    super(String.format(MESSAGE_WITH_ID, id));
  }
}
