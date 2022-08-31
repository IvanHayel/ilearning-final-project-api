package by.hayel.server.mapper.collection;

import by.hayel.server.mapper.user.ReadOnlyUserMapper;
import by.hayel.server.model.entity.collection.UserCollection;
import by.hayel.server.model.entity.collection.dto.ReadOnlyUserCollectionDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = "spring",
    uses = {ReadOnlyUserMapper.class, ThemeMapper.class},
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ReadOnlyUserCollectionMapper {
  ReadOnlyUserCollectionDto userCollectionToReadOnlyUserCollectionDto(UserCollection collection);
}
