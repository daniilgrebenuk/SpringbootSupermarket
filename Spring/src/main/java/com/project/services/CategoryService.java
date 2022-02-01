package com.project.services;

import com.project.model.exception.DataNotFoundException;
import com.project.model.product.Category;

import java.util.List;

public interface CategoryService {

  List<Category> findAll();

  Category add(Category category);

  Category update(Category category);

  boolean delete(Long id);
}
