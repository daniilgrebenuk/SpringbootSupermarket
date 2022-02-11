package com.project.repository.product;

import com.project.model.product.ProductSupply;
import com.project.model.product.keys.ProductSupplyKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSupplyRepository extends JpaRepository<ProductSupply, ProductSupplyKey> {

}
