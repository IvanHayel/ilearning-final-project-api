package by.hayel.server.web.controller.rest;

import by.hayel.server.model.entity.collection.dto.CollectionItemDto;
import by.hayel.server.model.entity.collection.dto.UserCollectionDto;
import by.hayel.server.service.search.HibernateSearchService;
import by.hayel.server.web.payload.ServerResponse;
import by.hayel.server.web.payload.request.SearchRequest;
import by.hayel.server.web.payload.response.GlobalSearchResponse;
import java.util.List;
import javax.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SearchController {
  HibernateSearchService searchService;

  @PostMapping
  public ResponseEntity<ServerResponse> searchGlobal(@Valid @RequestBody SearchRequest request) {
    var searchTerm = request.getSearchTerm();
    var response = new GlobalSearchResponse();
    response.setCollections(searchService.searchForCollections(searchTerm));
    response.setItems(searchService.searchForItems(searchTerm));
    return ResponseEntity.ok(response);
  }

  @PostMapping("/collections")
  public List<UserCollectionDto> searchInCollections(@Valid @RequestBody SearchRequest request) {
    return searchService.searchForCollections(request.getSearchTerm());
  }

  @PostMapping("/items")
  public List<CollectionItemDto> searchInItems(@Valid @RequestBody SearchRequest request) {
    return searchService.searchForItems(request.getSearchTerm());
  }

  @PostMapping("/themes")
  public List<UserCollectionDto> searchByTheme(@Valid @RequestBody SearchRequest request) {
    return searchService.searchForCollectionsByTheme(request.getSearchTerm());
  }

  @PostMapping("/tags")
  public List<CollectionItemDto> searchByTag(@Valid @RequestBody SearchRequest request) {
    return searchService.searchForItemsByTag(request.getSearchTerm());
  }
}
