package com.project.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.model.product.helper.ProductContainer;
import com.project.model.storage.Supply;
import com.project.model.product.keys.ProductSupplyKey;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ProductSupply implements ProductContainer {

  @EmbeddedId
  private ProductSupplyKey id;

  @ManyToOne
  @MapsId("productId")
  @JoinColumn(name = "product_id")
  @ToString.Exclude
  @JsonIgnore
  private Product product;

  @ManyToOne
  @MapsId("supplyId")
  @JoinColumn(name = "supply_id")
  @ToString.Exclude
  @JsonIgnore
  private Supply supplyProduct;

  private Integer amount;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    ProductSupply that = (ProductSupply) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
