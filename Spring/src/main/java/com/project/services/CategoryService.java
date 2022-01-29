package com.project.services;

import com.project.model.exception.DataNotFoundException;
import com.project.model.product.Category;
import com.project.repository.product.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
  private final CategoryRepository categoryRepository;

  @Autowired
  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public List<Category> findAll(){
    return categoryRepository.findAll();
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

  public boolean delete(Long id){
    Category category = categoryRepository.findById(id).orElseThrow(
        ()->new DataNotFoundException("Category with id " + id + " doesn't exist!")
    );
    categoryRepository.delete(category);
    return true;
  }
}
