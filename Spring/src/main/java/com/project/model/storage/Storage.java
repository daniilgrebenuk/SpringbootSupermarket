package com.project.model.storage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.model.product.ProductStorage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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

  @OneToMany(mappedBy = "storage", cascade = CascadeType.ALL)
  @ToString.Exclude
  @JsonIgnore
  private List<ProductStorage> products;

  @OneToMany(mappedBy = "storage", cascade = CascadeType.ALL)
  @ToString.Exclude
  @JsonIgnore
  private List<Supply> supplies;

  private String location;

  public void addProduct(ProductStorage productStorage){
    products.add(productStorage);
  }

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
