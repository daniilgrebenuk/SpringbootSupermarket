package com.project.model.product;

import com.project.model.customer.Order;
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
public class ProductOrder {
  @EmbeddedId
  private ProductOrderKey id;

  @ManyToOne
  @MapsId("productId")
  @JoinColumn(name = "product_id")
  private Product product;

  @ManyToOne
  @MapsId("orderId")
  @JoinColumn(name = "order_id")
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
