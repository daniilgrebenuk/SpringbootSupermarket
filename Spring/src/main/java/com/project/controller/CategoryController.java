package com.project.controller;

import com.project.model.product.Category;
import com.project.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
  private final CategoryService categoryService;

  @Autowired
  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @PostMapping("/add")
  //@PreAuthorize()
  public ResponseEntity<?> createNewCategory(@RequestBody @Valid Category category, Errors errors){
    if (errors.hasErrors()){
      return getResponseEntityWithErrors(errors);
    }
    return new ResponseEntity<>(categoryService.add(category), HttpStatus.CREATED);
  }

  @PutMapping("/update")
  //@PreAuthorize()
  public ResponseEntity<?> updateCategory(@RequestBody @Valid Category category, Errors errors){
    if (errors.hasErrors()){
      return getResponseEntityWithErrors(errors);
    }
    try {
      category = categoryService.update(category);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
    return ResponseEntity.ok(category);
  }

  private ResponseEntity<Map<String, String>> getResponseEntityWithErrors(Errors errors){
    return new ResponseEntity<>(
        errors
            .getFieldErrors()
            .stream()
            .collect(Collectors.toMap(
                FieldError::getField,
                f-> Optional
                    .ofNullable(f.getDefaultMessage())
                    .orElse(f.getObjectName()))
            ),
        HttpStatus.BAD_REQUEST
    );
  }
}
