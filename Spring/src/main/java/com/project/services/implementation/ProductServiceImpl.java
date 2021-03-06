package com.project.services.implementation;

import com.project.model.exception.DataNotFoundException;
import com.project.model.product.Product;
import com.project.model.product.helper.ProductResponse;
import com.project.repository.product.ProductRepository;
import com.project.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;


  @Override
  public Product findByProductId(Long id) {
    return productRepository
        .findById(id)
        .orElseThrow(
            () -> new DataNotFoundException(String.format("Product with id %d not found!", id))
        );
  }

  @Override
  public List<Product> findAll() {
    return productRepository.findAll();
  }


  @Override
  public Product add(Product product) {
    product.setId(null);
    return productRepository.save(product);
  }

  @Override
  public Product update(Product product) {
    if (product.getId() == null)
      throw new IllegalArgumentException("Unable to save product without id");
    return productRepository.save(product);
  }


  @Override
  public List<Product> findAllByCategoryId(Long id) {
    List<Product> products = productRepository.findAllByCategoryId(id);
    if (products.isEmpty())
      throw new DataNotFoundException("Category is empty or does not exist");
    return products;
  }

  @Override
  public boolean deleteById(Long id) {
    Product product = findByProductId(id);
    productRepository.delete(product);
    return true;
  }

  @Override
  public List<ProductResponse> findAllBySupplyId(Long supplyId) {
    return productRepository.findAllBySupplyId(supplyId);
  }

  @Override
  public List<Product> findAllByStorageId(Long storageId) {
    return productRepository.findAllByStorageId(storageId);
  }

  @Override
  public List<Product> findAllByOrderId(Long orderId) {
    return productRepository.findAllByOrderId(orderId);
  }
}