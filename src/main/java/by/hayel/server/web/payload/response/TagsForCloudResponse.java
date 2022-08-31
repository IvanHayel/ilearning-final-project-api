package by.hayel.server.web.payload.response;

import by.hayel.server.web.payload.ServerResponse;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TagsForCloudResponse implements ServerResponse {
  Map<String, Integer> tags;
}
