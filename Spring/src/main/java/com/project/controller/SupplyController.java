package com.project.controller;

import com.project.model.storage.Supply;
import com.project.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/supply")
public class SupplyController {
  private final StorageService storageService;

  @Autowired
  public SupplyController(StorageService storageService) {
    this.storageService = storageService;
  }

  @GetMapping("/get/current")
  //@PreAuthorize()
  public ResponseEntity<?> getAllSupplyForCurrentUser(){
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    return ResponseEntity.ok(
        storageService.getAllSupplyByUsername(username)
    );
  }

  @GetMapping("/all")
  //@PreAuthorize()
  public ResponseEntity<?> getAllSupply(){
    return ResponseEntity.ok(storageService.getAllSupply());
  }

  @PostMapping("/create")
  //@PreAuthorize()
  public ResponseEntity<?> createNewSupply(@RequestBody Supply supply){
    supply = storageService.createNewSupply(supply);
    return ResponseEntity.ok(supply);
  }

  @PutMapping("/add-employee")
  //@PreAuthorize()
  public ResponseEntity<?> addEmployee(@RequestBody Map<String, Long> body){
    storageService.addEmployeeToSupply(body.get("supply_id"), body.get("user_id"));
    return ResponseEntity.ok().build();
  }


}
