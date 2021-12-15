package com.project.services;

import com.project.model.exception.DataNotFoundException;
import com.project.model.product.Category;
import com.project.repository.product.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("<= CategoryController Test =>")
class CategoryServiceTest {
  @InjectMocks
  private CategoryService categoryService;

  @Mock
  private CategoryRepository categoryRepository;

  @Test
  @DisplayName("<= update correct Category with Id =>")
  void updateCorrectCategory(){
    Category category = new Category(1L, "Thing");
    when(categoryRepository.save(any(Category.class))).thenReturn(category);
    assertAll(
        ()->assertDoesNotThrow(()->categoryService.update(category)),
        ()->assertThat(categoryService.update(category)).isEqualTo(category)
    );
  }

  @Test
  @DisplayName("<= update Category without Id =>")
  void updateCategoryWithoutId(){
    assertThrows(
        IllegalArgumentException.class,
        ()->categoryService.update(new Category())
    );
  }
}