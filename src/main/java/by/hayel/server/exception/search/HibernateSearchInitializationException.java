package by.hayel.server.exception.search;

import java.io.Serial;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class HibernateSearchInitializationException extends RuntimeException {
  @Serial private static final long serialVersionUID = 1L;

  private static final String DEFAULT_MESSAGE = "Unable to initialize hibernate search!";

  public HibernateSearchInitializationException() {
    super(DEFAULT_MESSAGE);
  }
}
