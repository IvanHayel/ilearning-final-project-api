package by.hayel.server.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEntryPointJwt implements AuthenticationEntryPoint {
  private static final String STATUS_FIELD = "status";
  private static final String ERROR_FIELD = "error";
  private static final String MESSAGE_FIELD = "message";
  private static final String PATH_FIELD = "path";

  private static final String UNAUTHORIZED = "Unauthorized";

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException {
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    final Map<String, Object> body = mapToResponseBody(request, authException);
    final ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(response.getOutputStream(), body);
  }

  private Map<String, Object> mapToResponseBody(
      HttpServletRequest request, AuthenticationException authException) {
    final Map<String, Object> body = new HashMap<>();
    body.put(ERROR_FIELD, UNAUTHORIZED);
    body.put(PATH_FIELD, request.getServletPath());
    body.put(MESSAGE_FIELD, authException.getMessage());
    body.put(STATUS_FIELD, HttpServletResponse.SC_UNAUTHORIZED);
    return body;
  }
}
