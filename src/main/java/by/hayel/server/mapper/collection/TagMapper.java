package by.hayel.server.mapper.collection;

import by.hayel.server.model.entity.collection.Tag;
import by.hayel.server.model.entity.collection.dto.TagDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface TagMapper {
  Tag tagDtoToTag(TagDto tagDto);

  TagDto tagToTagDto(Tag tag);

  List<TagDto> tagsToTagDtos(List<Tag> tags);
}
