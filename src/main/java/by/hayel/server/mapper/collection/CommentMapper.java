package by.hayel.server.mapper.collection;

import by.hayel.server.mapper.user.ReadOnlyUserMapper;
import by.hayel.server.model.entity.collection.Comment;
import by.hayel.server.model.entity.collection.dto.CommentDto;
import java.util.List;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = "spring",
    uses = {ReadOnlyUserMapper.class, ReadOnlyCollectionItemMapper.class},
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CommentMapper {
  Comment commentDtoToComment(CommentDto commentDto);

  CommentDto commentToCommentDto(Comment comment);

  List<CommentDto> commentsToCommentDtos(List<Comment> comments);
}
