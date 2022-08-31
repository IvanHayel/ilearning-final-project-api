package by.hayel.server.mapper.collection;

import by.hayel.server.mapper.user.ReadOnlyUserMapper;
import by.hayel.server.model.entity.collection.UserCollection;
import by.hayel.server.model.entity.collection.dto.UserCollectionDto;
import java.util.List;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = "spring",
    uses = {ReadOnlyUserMapper.class, ThemeMapper.class, CollectionItemMapper.class},
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserCollectionMapper {
  UserCollectionDto userCollectionToUserCollectionDto(UserCollection userCollection);

  List<UserCollectionDto> userCollectionsToUserCollectionDtos(List<UserCollection> collections);
}
