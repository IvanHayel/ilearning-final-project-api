package by.hayel.server.mapper.collection;

import by.hayel.server.model.entity.collection.CollectionItem;
import by.hayel.server.model.entity.collection.dto.CollectionItemDto;
import java.util.List;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = "spring",
    uses = {
      ReadOnlyUserCollectionMapper.class,
      CommentMapper.class,
      ItemFieldMapper.class,
      LikeMapper.class,
      CommentMapper.class,
      TagMapper.class
    },
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CollectionItemMapper {
  CollectionItemDto collectionItemToCollectionItemDto(CollectionItem collectionItem);

  List<CollectionItemDto> collectionItemsToCollectionItemDtos(List<CollectionItem> items);
}
