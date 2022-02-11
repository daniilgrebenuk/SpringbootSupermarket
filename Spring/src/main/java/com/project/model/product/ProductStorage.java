package com.project.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.model.product.helper.ProductContainer;
import com.project.model.storage.Storage;
import com.project.model.product.keys.ProductStorageKey;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ProductStorage implements ProductContainer {
  @EmbeddedId
  private ProductStorageKey id = new ProductStorageKey();

  @ManyToOne
  @MapsId("productId")
  @JoinColumn(name = "product_id")
  @JsonIgnore
  @ToString.Exclude
  private Product product;

  @ManyToOne
  @MapsId("storageId")
  @JoinColumn(name = "storage_id")
  @JsonIgnore
  @ToString.Exclude
  private Storage storage;

  private Integer amount;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    ProductStorage that = (ProductStorage) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
