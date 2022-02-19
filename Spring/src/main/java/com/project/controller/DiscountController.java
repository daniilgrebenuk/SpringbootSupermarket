package com.project.controller;

import com.project.model.product.Discount;
import com.project.services.DiscountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/discount")
@RequiredArgsConstructor
@Slf4j
public class DiscountController {
  private final DiscountService discountService;

  @GetMapping("/all")
  //@PreAuthorize()
  public ResponseEntity<List<Discount>> allDiscounts(){
    return ResponseEntity.ok(discountService.findAll());
  }

  @PostMapping("/add/product")
  //@PreAuthorize()
  public ResponseEntity<?> addProductDiscount(@RequestBody Map<String, String> body){
    try{
      return ResponseEntity.ok(
          discountService.createDiscountForProduct(
              Long.parseLong(body.get("productId")),
              Integer.parseInt(body.get("discount"))
          )
      );
    } catch (Exception e) {
      log.warn(e.getMessage());
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }


}
