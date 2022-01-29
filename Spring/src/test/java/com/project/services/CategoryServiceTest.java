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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
  @DisplayName("<= find all categories =>")
  void findAll() {
    List<Category> categoryList = List.of(
        new Category(1L, "qwe"),
        new Category(2L, "qwe"),
        new Category(3L, "qwe"),
        new Category(4L, "qwe")
    );

    when(categoryRepository.findAll()).thenReturn(categoryList);

    assertThat(categoryService.findAll()).isEqualTo(categoryList);
  }

  @Test
  @DisplayName("<= add new Category =>")
  void addCategory(){
    Category category = new Category(1L,"category");
    when(categoryRepository.save(any(Category.class))).thenReturn(category);
    assertThat(categoryService.add(new Category(null,"category"))).isEqualTo(category);
  }

  @Test
  @DisplayName("<= update correct Category with Id =>")
  void updateCorrectCategory() {
    Category category = new Category(1L, "Thing");
    when(categoryRepository.save(any(Category.class))).thenReturn(category);
    assertAll(
        () -> assertDoesNotThrow(() -> categoryService.update(category)),
        () -> assertThat(categoryService.update(category)).isEqualTo(category)
    );
  }

  @Test
  @DisplayName("<= update Category without Id =>")
  void updateCategoryWithoutId() {
    assertThrows(
        IllegalArgumentException.class,
        () -> categoryService.update(new Category())
    );
  }

  @Test
  @DisplayName("<= delete Category with correct Id")
  void deleteCategoryWithCorrectId(){
    when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.of(new Category(1L, "123")));

    assertAll(
        () -> assertDoesNotThrow(() -> categoryService.delete(1L)),
        () -> assertThat(categoryService.delete(1L)).isTrue()
    );
  }

  @Test
  @DisplayName("<= delete Category that doesn't exist =>")
  void deleteCategoryThatDoesntExist(){
    when(categoryRepository.findById(any(Long.class))).thenReturn(Optional.empty());

    assertThatThrownBy(() -> categoryService.delete(1L)).isInstanceOf(DataNotFoundException.class);
  }
}