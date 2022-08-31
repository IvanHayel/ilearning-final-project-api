package by.hayel.server.web.payload.request;

import by.hayel.server.web.payload.ClientRequest;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignUpRequest implements ClientRequest {
  @NotBlank
  @Size(min = 3, max = 20)
  String username;

  @NotBlank
  @Size(max = 50)
  @Email
  String email;

  @NotBlank String password;
}
