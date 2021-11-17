package com.project.model.product.keys;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class ProductOrderKey implements Serializable {

  @Column(name = "product_id")
  private Long productId;

  @Column(name = "order_id")
  private Long orderId;
}
