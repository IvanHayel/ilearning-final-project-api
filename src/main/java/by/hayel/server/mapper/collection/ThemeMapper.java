package by.hayel.server.mapper.collection;

import by.hayel.server.model.entity.collection.Theme;
import by.hayel.server.model.entity.collection.dto.ThemeDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ThemeMapper {
  Theme themeDtoToTheme(ThemeDto themeDto);

  ThemeDto themeToThemeDto(Theme theme);

  List<ThemeDto> themesToThemeDtos(List<Theme> themes);
}
