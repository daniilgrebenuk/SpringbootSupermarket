package com.project.controller;

import com.project.model.exception.DataNotFoundException;
import com.project.model.product.Product;
import com.project.model.product.helper.ProductResponse;
import com.project.services.ProductService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@Slf4j
public class ProductController {
  private final ProductService productService;


  @GetMapping("/all")
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

  @GetMapping("/all-in-supply/{supplyId}")
  public ResponseEntity<List<ProductResponse>> getAllProductInSupply(@PathVariable Long supplyId){
    return ResponseEntity.ok(productService.findAllBySupplyId(supplyId));
  }

  @GetMapping("/all-in-storage/{storageId}")
  public ResponseEntity<List<Product>> getAllProductInStorage(@PathVariable Long storageId){
    return ResponseEntity.ok(productService.findAllByStorageId(storageId));
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
