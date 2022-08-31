package by.hayel.server.web.controller.rest;

import by.hayel.server.model.entity.collection.dto.CollectionItemDto;
import by.hayel.server.model.entity.collection.dto.TagDto;
import by.hayel.server.model.entity.collection.dto.ThemeDto;
import by.hayel.server.model.entity.collection.dto.UserCollectionDto;
import by.hayel.server.service.collection.CollectionsService;
import by.hayel.server.service.collection.TagService;
import by.hayel.server.service.collection.ThemeService;
import by.hayel.server.web.payload.ServerResponse;
import by.hayel.server.web.payload.request.CollectionRequest;
import by.hayel.server.web.payload.request.ItemRequest;
import java.util.List;
import javax.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/collections")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CollectionsController {
  CollectionsService collectionsService;
  ThemeService themeService;
  TagService tagService;

  @GetMapping
  public List<UserCollectionDto> getGlobalCollections(@RequestParam(required = false) String sort) {
    return collectionsService.getGlobalCollections();
  }

  @GetMapping("/own/list")
  public List<UserCollectionDto> getUserCollections(@RequestParam(required = false) String sort) {
    return collectionsService.getUserCollections();
  }

  @GetMapping("/top/{count}")
  public List<UserCollectionDto> getTopUserCollections(@PathVariable Long count) {
    return collectionsService.getTopUserCollections(count);
  }

  @GetMapping("/{name}")
  public UserCollectionDto getCollection(@PathVariable String name) {
    return collectionsService.getCollectionDtoByName(name);
  }

  @GetMapping("/{name}/items")
  public List<CollectionItemDto> getCollectionItems(
      @PathVariable String name, @RequestParam(required = false) String sort) {
    return collectionsService.getCollectionItems(name);
  }

  @GetMapping("/{name}/items/{id}")
  public CollectionItemDto getCollectionItem(@PathVariable String name, @PathVariable Long id) {
    return collectionsService.getCollectionItem(name, id);
  }

  @GetMapping("/tags")
  public List<TagDto> getTags() {
    return tagService.getTags();
  }

  @GetMapping("/tags-for-cloud")
  public ResponseEntity<ServerResponse> getTagsForCloud() {
    return tagService.getTagsForCloud();
  }

  @GetMapping("/themes")
  public List<ThemeDto> getThemes() {
    return themeService.getThemes();
  }

  @PostMapping
  @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'ROOT')")
  public void addCollection(@Valid @RequestBody CollectionRequest request) {
    collectionsService.addCollection(request);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'ROOT')")
  public void deleteCollection(@PathVariable Long id) {
    collectionsService.deleteCollection(id);
    tagService.verifyTags();
  }

  @PutMapping
  @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'ROOT')")
  public void updateCollection(@Valid @RequestBody CollectionRequest request) {
    collectionsService.updateCollection(request);
    themeService.verifyThemes();
  }

  @PostMapping("/{name}/items")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'ROOT')")
  public void addItemToCollection(
      @PathVariable String name, @Valid @RequestBody ItemRequest request) {
    collectionsService.addItemToCollection(name, request);
  }

  @PutMapping("/{name}/items/{id}")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'ROOT')")
  public void updateCollectionItem(
      @PathVariable String name, @Valid @RequestBody ItemRequest request) {
    collectionsService.updateCollectionItem(name, request);
    tagService.verifyTags();
  }

  @DeleteMapping("/{name}/items/{id}")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'ROOT')")
  public void deleteItemFromCollection(@PathVariable String name, @PathVariable Long id) {
    collectionsService.deleteItemFromCollection(name, id);
    tagService.verifyTags();
  }

  @PatchMapping("/{name}/items/{id}")
  @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'ROOT')")
  public void likeItem(@PathVariable String name, @PathVariable Long id) {
    collectionsService.likeItem(id);
  }
}
