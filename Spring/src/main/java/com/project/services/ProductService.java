package com.project.services;

import com.project.model.exception.DataNotFoundException;
import com.project.model.exception.IdNotPresentException;
import com.project.model.product.Product;
import com.project.repository.product.CategoryRepository;
import com.project.repository.product.DiscountRepository;
import com.project.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  public Product addProduct(Product product){
    return productRepository.save(product);
  }

  public Product updateProduct(Product product){
    if(product.getId() == null)
      throw new IdNotPresentException("Product has no ID");
    return productRepository.save(product);
  }
}