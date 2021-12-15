package com.project.services;

import com.project.model.product.Category;
import com.project.repository.product.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
  private final CategoryRepository categoryRepository;

  @Autowired
  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public Category add(Category category){
    category.setId(null);
    return categoryRepository.save(category);
  }

  public Category update(Category category){
    if(category.getId() == null)
      throw new IllegalArgumentException("Unable to save product without id");
    return categoryRepository.save(category);
  }
}
