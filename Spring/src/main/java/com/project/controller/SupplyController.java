package com.project.controller;

import com.project.model.storage.Supply;
import com.project.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/supply")
public class SupplyController {
  private final StorageService storageService;

  @Autowired
  public SupplyController(StorageService storageService) {
    this.storageService = storageService;
  }

  @PostMapping("/create")
  public ResponseEntity<?> createNewSupply(@RequestBody Supply supply){
    supply = storageService.createNewSupply(supply);
    return ResponseEntity.ok(supply);
  }
}
