package com.project.services.implementation;

import com.project.model.exception.DataNotFoundException;
import com.project.model.product.Category;
import com.project.model.product.Discount;
import com.project.model.product.Product;
import com.project.repository.product.DiscountRepository;
import com.project.services.CategoryService;
import com.project.services.DiscountService;
import com.project.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

  private final DiscountRepository discountRepository;
  private final ProductService productService;
  private final CategoryService categoryService;

  @Override
  public Discount findById(Long discountId) {
    return discountRepository
        .findById(discountId)
        .orElseThrow(()->new DataNotFoundException("Discount with id " + discountId + " doesn't exist!"));
  }

  @Override
  public Discount createDiscountForProduct(Long productId, Integer discount) {
    Product product = productService.findByProductId(productId);
    Discount temp = new Discount();
    temp.setProduct(product);
    temp.setDiscount(discount);
    temp.setExpireDate(LocalDate.now().plusDays(3));
    temp.setIsGlobal(false);
    return discountRepository.save(temp);
  }

  @Override
  public Discount createDiscountForCategory(Long categoryId, Integer discount) {
    Category category = categoryService.findById(categoryId);
    Discount temp = new Discount();
    temp.setCategory(category);
    temp.setDiscount(discount);
    temp.setExpireDate(LocalDate.now().plusDays(3));
    temp.setIsGlobal(false);
    return discountRepository.save(temp);
  }

  @Override
  public void deleteDiscount(Long discountId) {
    Discount discount = findById(discountId);
    discountRepository.delete(discount);
  }

  @Override
  public List<Discount> findAll() {
    return discountRepository.findAll();
  }
}
