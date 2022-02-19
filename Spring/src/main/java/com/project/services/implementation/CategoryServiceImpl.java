package com.project.services.implementation;

import com.project.model.exception.DataNotFoundException;
import com.project.model.product.Category;
import com.project.repository.product.CategoryRepository;
import com.project.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;


  @Override
  public Category findById(Long categoryId) {
    return categoryRepository
        .findById(categoryId)
        .orElseThrow(()->new DataNotFoundException("Category with id " + categoryId + " doesn't exist!"));
  }

  public List<Category> findAll() {
    return categoryRepository.findAll();
  }

  public Category add(Category category) {
    category.setId(null);
    return categoryRepository.save(category);
  }

  public Category update(Category category) {
    if (category.getId() == null)
      throw new IllegalArgumentException("Unable to save product without id");
    return categoryRepository.save(category);
  }

  public boolean delete(Long id) {
    Category category = findById(id);
    categoryRepository.delete(category);
    return true;
  }
}
