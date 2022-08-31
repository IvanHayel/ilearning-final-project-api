package by.hayel.server.mapper.user;

import by.hayel.server.model.entity.user.User;
import by.hayel.server.model.entity.user.dto.UserDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
  UserDto userToUserDto(User user);

  List<UserDto> usersToUserDtos(List<User> users);
}
