package by.hayel.server.web.payload.response;

import by.hayel.server.web.payload.ServerResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StatisticsResponse implements ServerResponse {
  int collectionsCount;
  int likesCount;
  int commentsCount;
}
