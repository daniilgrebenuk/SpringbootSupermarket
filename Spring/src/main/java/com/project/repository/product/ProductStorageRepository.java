package com.project.repository.product;

import com.project.model.product.ProductStorage;
import com.project.model.product.keys.ProductStorageKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductStorageRepository extends JpaRepository<ProductStorage, ProductStorageKey> {

}
