package by.hayel.server.mapper.collection;

import by.hayel.server.mapper.user.ReadOnlyUserMapper;
import by.hayel.server.model.entity.collection.Like;
import by.hayel.server.model.entity.collection.dto.LikeDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = "spring",
    uses = {ReadOnlyUserMapper.class},
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface LikeMapper {
  Like likeDtoToLike(LikeDto likeDto);

  LikeDto likeToLikeDto(Like like);
}
