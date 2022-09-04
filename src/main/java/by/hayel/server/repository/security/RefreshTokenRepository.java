package by.hayel.server.repository.security;

import by.hayel.server.model.entity.token.RefreshToken;
import by.hayel.server.model.entity.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByToken(String token);

  void deleteAllByUser(User user);
}
