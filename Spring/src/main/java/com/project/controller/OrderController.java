package com.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.model.customer.Order;
import com.project.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
  private final OrderService orderService;
  private final ObjectMapper objectMapper;

  @GetMapping("/all")
  //PreAuthorize()
  public ResponseEntity<List<Order>> findAllOrder(){

    return ResponseEntity.ok(orderService.findAll());
  }

  @PostMapping("/add")
  public ResponseEntity<?> addOrder(@RequestBody Map<String,String> body){
    try {
      return ResponseEntity.ok(orderService.addOrder(
          objectMapper.readValue(body.get("order"), Order.class),
          objectMapper.readValue(body.get("products"), new TypeReference<>() {})
      ));
    } catch (JsonProcessingException e) {
      log.warn(e.getMessage());
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

}