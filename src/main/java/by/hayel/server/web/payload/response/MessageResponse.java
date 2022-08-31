package by.hayel.server.web.payload.response;

import by.hayel.server.web.payload.ServerResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageResponse implements ServerResponse {
  @NonNull String message;
}
