package com.project.controller;

import com.project.model.storage.Storage;
import com.project.services.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/storage")
@RequiredArgsConstructor
public class StorageController {

  private final StorageService storageService;

  @GetMapping("/all")
  //@PreAuthorize()
  public ResponseEntity<?> getAllStorage(){
    return ResponseEntity.ok(storageService.getAllStorage());
  }

  @PostMapping("/add")
  //@PreAuthorize()
  public ResponseEntity<?> addStorage(@RequestBody Storage storage){
    return ResponseEntity.ok(storageService.addStorage(storage));
  }
}
