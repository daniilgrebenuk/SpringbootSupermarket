package com.project.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.model.customer.Order;
import com.project.model.product.helper.ProductContainer;
import com.project.model.product.keys.ProductOrderKey;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ProductOrder implements ProductContainer {
  @EmbeddedId
  private ProductOrderKey id = new ProductOrderKey();

  @ManyToOne
  @MapsId("productId")
  @JoinColumn(name = "product_id")
  @ToString.Exclude
  @JsonIgnore
  private Product product;

  @ManyToOne
  @MapsId("orderId")
  @JoinColumn(name = "order_id")
  @ToString.Exclude
  @JsonIgnore
  private Order order;

  private Integer amount;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    ProductOrder that = (ProductOrder) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
