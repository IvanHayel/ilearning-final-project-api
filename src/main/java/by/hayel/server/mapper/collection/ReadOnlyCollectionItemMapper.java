package by.hayel.server.mapper.collection;

import by.hayel.server.model.entity.collection.CollectionItem;
import by.hayel.server.model.entity.collection.dto.ReadOnlyCollectionItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ReadOnlyCollectionItemMapper {
  ReadOnlyCollectionItemDto collectionItemToReadOnlyCollectionItemDto(CollectionItem item);
}
