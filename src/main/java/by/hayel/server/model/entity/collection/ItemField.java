package by.hayel.server.model.entity.collection;

import by.hayel.server.model.entity.BaseEntity;
import java.io.Serial;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;

@Entity
@Table(name = "item_fields")
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemField extends BaseEntity {
  @Serial private static final long serialVersionUID = 1L;

  @FullTextField
  @Column(name = "field_name", nullable = false)
  String name;

  @FullTextField
  @Column(name = "field_value", nullable = false)
  String value;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  ContentType contentType;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  CollectionItem item;
}
