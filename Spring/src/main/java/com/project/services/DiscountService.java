package com.project.services;

import com.project.model.product.Discount;

public interface DiscountService {

  Discount createDiscountForProduct(Long productId, Integer discount);

  Discount createDiscountForCategory(Long categoryId, Integer discount);

  void deleteDiscount(Long discountId);
}
