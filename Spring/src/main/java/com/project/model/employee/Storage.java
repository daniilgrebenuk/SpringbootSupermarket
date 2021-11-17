package com.project.model.employee;

import com.project.model.product.Product;
import com.project.model.product.ProductStorage;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Storage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(mappedBy = "storage")
  @ToString.Exclude
  private List<ProductStorage> products;

  private String location;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Storage storage = (Storage) o;
    return id != null && Objects.equals(id, storage.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

}
