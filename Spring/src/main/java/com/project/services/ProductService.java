package com.project.services;

import com.project.model.exception.DataNotFoundException;
import com.project.model.product.Category;
import com.project.model.product.Product;
import com.project.repository.product.CategoryRepository;
import com.project.repository.product.DiscountRepository;
import com.project.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
  private final ProductRepository productRepository;
  private final DiscountRepository discountRepository;
  private final CategoryRepository categoryRepository;

  @Autowired
  public ProductService(ProductRepository productRepository,
                        DiscountRepository discountRepository,
                        CategoryRepository categoryRepository) {
    this.productRepository = productRepository;
    this.discountRepository = discountRepository;
    this.categoryRepository = categoryRepository;
  }

  public Product findByProductId(Long id) {
    return productRepository
        .findById(id)
        .orElseThrow(
            () -> new DataNotFoundException(String.format("Product with id %d not found!", id))
        );
  }

  public List<Product> findAll(){
    return productRepository.findAll();
  }

  public Product add(Product product){
    product.setId(null);
    return productRepository.save(product);
  }

  public Product update(Product product){
    if(product.getId() == null)
      throw new IllegalArgumentException("Unable to save product without id");
    return productRepository.save(product);
  }


  public List<Product> findAllByCategoryId(Long id) {
    List<Product> products = productRepository.findAllByCategoryId(id);
    if (products.isEmpty())
      throw new DataNotFoundException("Category is empty or does not exist");
    return products;
  }

  public boolean delete(Long id){
    Product product = findByProductId(id);
    productRepository.delete(product);
    return true;
  }
}