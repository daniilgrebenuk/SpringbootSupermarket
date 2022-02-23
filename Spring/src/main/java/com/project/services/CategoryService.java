package com.project.services;

import com.project.model.product.Category;

import java.util.List;

public interface CategoryService {

  Category findById(Long categoryId);

  List<Category> findAll();

  Category add(Category category);

  Category update(Category category);

  boolean delete(Long id);
}
