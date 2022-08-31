package by.hayel.server.exception.security;

import java.io.Serial;

public class BlockedUserException extends RuntimeException {
  @Serial private static final long serialVersionUID = 1L;

  private static final String DEFAULT_MESSAGE = "User blocked!";
  private static final String MESSAGE_WITH_ID = "User with id %d blocked!";

  public BlockedUserException() {
    super(DEFAULT_MESSAGE);
  }

  public BlockedUserException(Long id) {
    super(String.format(MESSAGE_WITH_ID, id));
  }
}
