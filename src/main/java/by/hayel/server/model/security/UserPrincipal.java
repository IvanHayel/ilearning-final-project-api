package by.hayel.server.model.security;

import by.hayel.server.model.entity.user.User;
import java.io.Serial;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserPrincipal implements UserDetails, OAuth2User {
  @Serial private static final long serialVersionUID = 1L;

  Long id;
  String username;
  String password;
  String email;
  @Setter String name;
  ZonedDateTime createdAt;
  ZonedDateTime updatedAt;
  ZonedDateTime lastLogin;
  Collection<? extends GrantedAuthority> authorities;
  @Setter Map<String, Object> attributes;
  boolean isActive;
  boolean isBlocked;

  public static UserPrincipal build(User user) {
    var authorities =
        user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getName().name()))
            .toList();
    return new UserPrincipal(
        user.getId(),
        user.getUsername(),
        user.getPassword(),
        user.getEmail(),
        null,
        user.getCreateDate(),
        user.getUpdateDate(),
        user.getLastLogin(),
        authorities,
        null,
        user.isActive(),
        user.isBlocked());
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !isBlocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return !isBlocked;
  }
}
