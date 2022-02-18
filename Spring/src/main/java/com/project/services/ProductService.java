package com.project.services;

import com.project.model.product.Product;

import java.util.List;

public interface ProductService {

  Product findByProductId(Long id);

  List<Product> findAll();

  Product add(Product product);

  Product update(Product product);

  List<Product> findAllByCategoryId(Long id);

  boolean delete(Long id);

  List<Product> findAllBySupplyId(Long supplyId);

  List<Product> findAllByStorageId(Long storageId);

  List<Product> findAllByOrderId(Long orderId);
}
