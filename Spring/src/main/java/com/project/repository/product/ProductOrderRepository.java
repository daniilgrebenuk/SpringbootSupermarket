package com.project.repository.product;

import com.project.model.product.ProductOrder;
import com.project.model.product.keys.ProductOrderKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, ProductOrderKey> {

}
