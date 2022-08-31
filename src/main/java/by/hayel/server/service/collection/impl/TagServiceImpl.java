package by.hayel.server.service.collection.impl;

import by.hayel.server.mapper.collection.TagMapper;
import by.hayel.server.model.entity.collection.Tag;
import by.hayel.server.model.entity.collection.dto.TagDto;
import by.hayel.server.repository.collection.TagRepository;
import by.hayel.server.service.collection.TagService;
import by.hayel.server.web.payload.ServerResponse;
import by.hayel.server.web.payload.response.TagsForCloudResponse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TagServiceImpl implements TagService {
  TagRepository repository;
  TagMapper mapper;

  @Override
  public List<TagDto> getTags() {
    return mapper.tagsToTagDtos(repository.findAll());
  }

  @Override
  public Set<Tag> getTags(List<String> tagNames) {
    Set<Tag> tags = new HashSet<>();
    for (String tagName : tagNames) {
      if (!repository.existsByName(tagName)) {
        Tag tag = new Tag();
        tag.setName(tagName);
        repository.save(tag);
      }
      tags.add(getTag(tagName));
    }
    return tags;
  }

  @Override
  public Tag getTag(String tagName) {
    return repository.findByName(tagName);
  }

  @Override
  public boolean exists(String tagName) {
    return repository.existsByName(tagName);
  }

  @Override
  @Transactional
  public void removeIfNoChild(Tag tag) {
    if (tag.getItems().isEmpty()) {
      repository.delete(tag);
    }
  }

  @Override
  @Transactional
  public void verifyTags() {
    List<Tag> tags = repository.findAll();
    tags.forEach(this::removeIfNoChild);
  }

  @Override
  @Transactional
  public ResponseEntity<ServerResponse> getTagsForCloud() {
    var tags = repository.findAll();
    Map<String, Integer> tagsForCloud = new HashMap<>();
    tags.forEach(tag -> tagsForCloud.put(tag.getName(), tag.getItems().size()));
    return ResponseEntity.ok(new TagsForCloudResponse(tagsForCloud));
  }
}
