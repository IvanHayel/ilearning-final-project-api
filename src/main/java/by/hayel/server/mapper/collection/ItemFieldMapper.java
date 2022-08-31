package by.hayel.server.mapper.collection;

import by.hayel.server.model.entity.collection.ItemField;
import by.hayel.server.model.entity.collection.dto.ItemFieldDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ItemFieldMapper {
  ItemField itemFieldDtoToItemField(ItemFieldDto itemFieldDto);

  ItemFieldDto itemFieldToItemFieldDto(ItemField itemField);
}
