package com.project.controller;

import com.project.model.exception.DataNotFoundException;
import com.project.model.product.Product;
import com.project.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/product")
@Slf4j
public class ProductController {
  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/all")
  //@PreAuthorize()
  public ResponseEntity<List<Product>> allProducts(){
    return ResponseEntity.ok(productService.findAll());
  }

  @GetMapping("/all/category/{id}")
  public ResponseEntity<?> allProductsByCategoryId(@PathVariable Long id){
    try{
      List<Product> products= productService.findAllByCategoryId(id);
      return ResponseEntity.ok(products);
    }catch (DataNotFoundException e){
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping("/add")
  //@PreAuthorize()
  public ResponseEntity<?> createNewProduct(@RequestBody @Valid Product product, Errors errors){
    if (errors.hasErrors()){
      return getResponseEntityWithErrors(errors);
    }
    return new ResponseEntity<>(productService.add(product), HttpStatus.CREATED);
  }

  @PutMapping("/update")
  //@PreAuthorize()
  public ResponseEntity<?> updateProduct(@RequestBody @Valid Product product, Errors errors){
    if (errors.hasErrors()){
      return getResponseEntityWithErrors(errors);
    }
    try {
      product = productService.update(product);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
    return ResponseEntity.ok(product);
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
