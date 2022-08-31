package by.hayel.server.web.payload.request;

import by.hayel.server.web.payload.ClientRequest;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignInRequest implements ClientRequest {
  @NotBlank String username;

  @NotBlank String password;
}
