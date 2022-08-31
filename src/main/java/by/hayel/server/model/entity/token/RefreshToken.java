package by.hayel.server.model.entity.token;

import by.hayel.server.model.entity.BaseEntity;
import by.hayel.server.model.entity.user.User;
import java.io.Serial;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity(name = "refresh_token")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshToken extends BaseEntity {
  @Serial private static final long serialVersionUID = 1L;

  @OneToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  User user;

  @Column(nullable = false)
  String token;

  @Column(nullable = false)
  Instant expiryDate;
}
