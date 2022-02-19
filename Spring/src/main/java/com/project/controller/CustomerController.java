package com.project.controller;

import com.project.model.customer.Customer;
import com.project.services.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

  private final CustomerService customerService;

  @GetMapping("/all")
  //@PreAuthorize()
  public ResponseEntity<List<Customer>> getAllCustomer() {
    return ResponseEntity.ok(customerService.findAll());
  }

  @GetMapping("/current")
  public ResponseEntity<?> getCurrentCustomer() {
    try {
      return ResponseEntity.ok(customerService.getCurrentCustomer());
    } catch (Exception e) {
      log.warn(e.getMessage());
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping("/add")
  public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
    return ResponseEntity.ok(customerService.add(customer));
  }

  @PatchMapping("/discount/add")
  public ResponseEntity<?> addDiscountToCustomer(@RequestBody Map<String, String> map) {
    try {
      return ResponseEntity.ok(
          customerService.addDiscountToCustomer(
              Long.parseLong(map.get("customerId")),
              Long.parseLong(map.get("discountId"))
          )
      );
    } catch (Exception e) {
      log.warn(e.getMessage());
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
