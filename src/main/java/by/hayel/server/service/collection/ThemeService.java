package by.hayel.server.service.collection;

import by.hayel.server.model.entity.collection.Theme;
import by.hayel.server.model.entity.collection.dto.ThemeDto;
import java.util.List;

public interface ThemeService {
  List<ThemeDto> getThemes();

  Theme get(String name);

  boolean exists(String name);

  void addThemeIfNotExist(Theme theme);

  void removeIfNoChild(Theme theme);

  void verifyThemes();
}
