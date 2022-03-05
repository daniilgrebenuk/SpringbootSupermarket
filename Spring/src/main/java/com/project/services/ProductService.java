package com.project.services;

import com.project.model.product.Product;
import com.project.model.product.helper.ProductResponse;

import java.util.List;

public interface ProductService {

  Product findByProductId(Long id);

  List<Product> findAll();

  Product add(Product product);

  Product update(Product product);

  List<Product> findAllByCategoryId(Long id);

  boolean deleteById(Long id);

  List<ProductResponse> findAllBySupplyId(Long supplyId);

  List<Product> findAllByStorageId(Long storageId);

  List<Product> findAllByOrderId(Long orderId);
}
