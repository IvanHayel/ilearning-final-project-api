package by.hayel.server.service.security.impl;

import by.hayel.server.model.entity.user.User;
import by.hayel.server.model.security.UserPrincipal;
import by.hayel.server.repository.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDetailsServiceImpl implements UserDetailsService {
  private static final String USERNAME_NOT_FOUND_MESSAGE_FORMAT = "User \"%s\" not found!";

  UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user =
        userRepository
            .findByUsername(username)
            .orElseThrow(
                () ->
                    new UsernameNotFoundException(
                        String.format(USERNAME_NOT_FOUND_MESSAGE_FORMAT, username)));
    return UserPrincipal.build(user);
  }
}
