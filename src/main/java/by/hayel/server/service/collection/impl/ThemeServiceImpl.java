package by.hayel.server.service.collection.impl;

import by.hayel.server.mapper.collection.ThemeMapper;
import by.hayel.server.model.entity.collection.Theme;
import by.hayel.server.model.entity.collection.dto.ThemeDto;
import by.hayel.server.repository.collection.ThemeRepository;
import by.hayel.server.service.collection.ThemeService;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ThemeServiceImpl implements ThemeService {
  ThemeRepository repository;
  ThemeMapper mapper;

  @Override
  public List<ThemeDto> getThemes() {
    return mapper.themesToThemeDtos(repository.findAll());
  }

  @Override
  public Theme get(String name) {
    return repository.findByName(name);
  }

  @Override
  public boolean exists(String name) {
    return repository.existsByName(name);
  }

  @Override
  @Transactional
  public void addThemeIfNotExist(Theme theme) {
    if (!exists(theme.getName())) {
      repository.save(theme);
    }
  }

  @Override
  @Transactional
  public void removeIfNoChild(Theme theme) {
    if (theme.getCollections().isEmpty()) {
      repository.delete(theme);
    }
  }

  @Override
  @Transactional
  public void verifyThemes() {
    List<Theme> themes = repository.findAll();
    themes.forEach(this::removeIfNoChild);
  }
}
