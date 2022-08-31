package by.hayel.server.mapper.user;

import by.hayel.server.model.entity.user.User;
import by.hayel.server.model.entity.user.dto.ReadOnlyUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ReadOnlyUserMapper {
  ReadOnlyUserDto userToReadOnlyUserDto(User user);

  User readOnlyUserDtoToUser(ReadOnlyUserDto readOnlyUserDto);
}
