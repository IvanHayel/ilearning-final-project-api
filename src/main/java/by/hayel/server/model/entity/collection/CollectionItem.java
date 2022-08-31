package by.hayel.server.model.entity.collection;

import by.hayel.server.model.entity.BaseEntity;
import java.io.Serial;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
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
@Table(name = "collection_items")
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CollectionItem extends BaseEntity {
  @Serial private static final long serialVersionUID = 1L;

  @FullTextField
  @Column(name = "item_name", nullable = false, unique = true)
  String name;

  @Column(name = "image_link", nullable = false)
  String imageLink;

  @Column(name = "image_public_id", nullable = false)
  String imagePublicId;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  UserCollection collection;

  @IndexedEmbedded
  @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
  List<ItemField> fields = new ArrayList<>();

  @IndexedEmbedded
  @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
  List<Comment> comments = new ArrayList<>();

  @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
  List<Like> likes = new ArrayList<>();

  @IndexedEmbedded
  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(name = "item_tags")
  Set<Tag> tags = new HashSet<>();

  public void setTags(Set<Tag> tags) {
    this.tags.forEach(tag -> tag.getItems().remove(this));
    this.tags = tags;
  }

  public void setFields(List<ItemField> fields) {
    this.fields.clear();
    if (fields != null) {
      this.fields.addAll(fields);
      this.fields.forEach(field -> field.setItem(this));
    }
  }

  public void setComments(List<Comment> comments) {
    this.comments.clear();
    if (comments != null) {
      this.comments.addAll(comments);
      this.comments.forEach(comment -> comment.setItem(this));
    }
  }

  @PrePersist
  void prePersist() {
    this.fields.forEach(field -> field.setItem(this));
  }
}
