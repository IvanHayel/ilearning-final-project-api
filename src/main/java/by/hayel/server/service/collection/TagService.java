package by.hayel.server.service.collection;

import by.hayel.server.model.entity.collection.Tag;
import by.hayel.server.model.entity.collection.dto.TagDto;
import by.hayel.server.web.payload.ServerResponse;
import java.util.List;
import java.util.Set;
import org.springframework.http.ResponseEntity;

public interface TagService {
  List<TagDto> getTags();

  Set<Tag> getTags(List<String> tagNames);

  Tag getTag(String tagName);

  boolean exists(String tagName);

  void removeIfNoChild(Tag tag);

  void verifyTags();

  ResponseEntity<ServerResponse> getTagsForCloud();
}
