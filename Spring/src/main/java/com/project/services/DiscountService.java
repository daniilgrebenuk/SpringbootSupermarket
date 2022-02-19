package com.project.services;

import com.project.model.product.Discount;

import java.util.List;

public interface DiscountService {

  Discount findById(Long discountId);

  Discount createDiscountForProduct(Long productId, Integer discount);

  Discount createDiscountForCategory(Long categoryId, Integer discount);

  void deleteDiscount(Long discountId);

  List<Discount> findAll();
}
