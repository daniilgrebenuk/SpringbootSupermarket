package com.project.controller;

import com.project.model.storage.Supply;
import com.project.services.SupplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/supply")
@RequiredArgsConstructor
@Slf4j
public class SupplyController {

  private final SupplyService supplyService;


  @GetMapping("/get/{id}")
  public ResponseEntity<?> getSupplyById(@PathVariable Long id){
    try{
      return ResponseEntity.ok(supplyService.findBySupplyId(id));
    }catch (Exception e){
      log.warn(e.getMessage());
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/get/current")
  //@PreAuthorize()
  public ResponseEntity<?> getAllSupplyForCurrentUser() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    return ResponseEntity.ok(supplyService.getAllSupplyByUsername(username));
  }

  @GetMapping("/all")
  //@PreAuthorize()
  public ResponseEntity<?> getAllSupply() {
    return ResponseEntity.ok(supplyService.getAllSupply());
  }

  @GetMapping("/all-by-storage-id/{id}")
  public ResponseEntity<?> getAllSupplyByStorageId(@PathVariable Long id){
    try {
      return ResponseEntity.ok(supplyService.findByStorageId(id));
    }catch (Exception e){
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping("/add")
  //@PreAuthorize()
  public ResponseEntity<?> addSupply(@RequestBody Map<String, String> body) {
    Long storageId = null;
    LocalDate date = null;
    try {
      storageId = Long.parseLong(body.get("storageId"));
      date = LocalDate.parse(body.get("date"));
    } catch (Exception e) {
      log.warn(e.getMessage());
      return ResponseEntity.badRequest().body("Not correct body");
    }

    Supply supply = supplyService.addSupply(storageId, date);
    return ResponseEntity.ok(supply);
  }


  @PatchMapping("/add-all-employee")
  //@PreAuthorize()
  public ResponseEntity<?> addAllEmployee(@RequestBody List<Map<String, Long>> body) {
    try {
      body.forEach(map -> supplyService.addEmployeeToSupply(map.get("supplyId"), map.get("employeeId")));
    } catch (Exception e) {
      log.warn(e.getMessage());
      return ResponseEntity.badRequest().body("Not correct body");
    }
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/add-employee")
  //@PreAuthorize()
  public ResponseEntity<?> addEmployee(@RequestBody Map<String, Long> body) {
    try {
      supplyService.addEmployeeToSupply(body.get("supplyId"), body.get("employeeId"));
    } catch (Exception e) {
      log.warn(e.getMessage());
      return ResponseEntity.badRequest().body("Not correct body");
    }
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/add-all-products")
  //@PreAuthorize()
  public ResponseEntity<?> addAllProducts(@RequestBody List<Map<String, String>> body) {
    try {
      body.forEach(map ->
          supplyService.addProductToSupply(
              Long.parseLong(map.get("supplyId")),
              Long.parseLong(map.get("productId")),
              Integer.parseInt(map.get("amount")
              )
          )
      );
    } catch (Exception e) {
      log.warn(e.getMessage());
      return ResponseEntity.badRequest().body("Not correct body");
    }

    return ResponseEntity.ok().build();
  }

  @PatchMapping("/add-product")
  //@PreAuthorize()
  public ResponseEntity<?> addProduct(@RequestBody Map<String, String> body) {
    try {
      supplyService.addProductToSupply(
          Long.parseLong(body.get("supplyId")),
          Long.parseLong(body.get("productId")),
          Integer.parseInt(body.get("amount"))
      );
    } catch (Exception e) {
      log.warn(e.getMessage());
      return ResponseEntity.badRequest().body("Not correct body");
    }
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/accept/{supplyId}")
  //@PreAuthorize()
  public ResponseEntity<?> acceptSupply(@PathVariable Long supplyId){
    try {
      supplyService.acceptSupplyById(supplyId);
    } catch (Exception e) {
      log.warn(e.getMessage());
      return ResponseEntity.badRequest().body("Not correct path variable");
    }
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> deleteSupplyById(@PathVariable Long id){
    try {
      this.supplyService.deleteSupplyById(id);
      return ResponseEntity.ok("Successfully deleted!");
    } catch (Exception e){
      log.warn(e.getMessage());
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
