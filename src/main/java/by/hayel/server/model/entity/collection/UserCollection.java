package by.hayel.server.model.entity.collection;

import by.hayel.server.model.entity.BaseEntity;
import by.hayel.server.model.entity.user.User;
import java.io.Serial;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

@Entity
@Indexed
@Table(name = "collections")
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCollection extends BaseEntity {
  @Serial private static final long serialVersionUID = 1L;

  @FullTextField
  @Column(name = "collection_name", nullable = false)
  String name;

  @IndexedEmbedded
  @ManyToOne(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "theme_id", nullable = false)
  Theme theme;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "owner_id", nullable = false)
  User owner;

  @Column(name = "image_link", nullable = false)
  String imageLink;

  @Column(name = "image_public_id", nullable = false)
  String imagePublicId;

  @OneToMany(mappedBy = "collection", cascade = CascadeType.ALL, orphanRemoval = true)
  List<CollectionItem> items;

  public void setTheme(Theme theme) {
    if (this.theme != null && !this.theme.equals(theme)) {
      this.theme.getCollections().remove(this);
    }
    this.theme = theme;
    theme.getCollections().add(this);
  }
}
