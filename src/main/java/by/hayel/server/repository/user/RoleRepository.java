package by.hayel.server.repository.user;

import by.hayel.server.model.entity.user.Role;
import by.hayel.server.model.entity.user.RoleName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(RoleName name);
}
