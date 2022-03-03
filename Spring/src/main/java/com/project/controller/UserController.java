package com.project.controller;

import com.project.model.credential.RegistrationForm;
import com.project.model.credential.User;
import com.project.repository.credential.UserRepository;
import com.project.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class UserController {
  private final UserService userService;

  @GetMapping("/all")
  //@PreAuthorize()
  public ResponseEntity<List<User>> findAll(){
    return ResponseEntity.ok(userService.findAll());
  }

  @GetMapping("/get/{id}")
  //@PreAuthorize()
  public ResponseEntity<?> findById(@PathVariable Long id){
    try {
      return ResponseEntity.ok(userService.findUserById(id));
    } catch (Exception e) {
      log.warn(e.getMessage());
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping("/registration")
  public ResponseEntity<?> registration(@RequestBody RegistrationForm form) {
    try {
      User user = userService.regNewUserFromRegistrationForm(form);
      log.info(user.toString());
      return ResponseEntity.ok(user);
    } catch (Exception e) {
      log.warn(e.getMessage());
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/update")
  //@PreAuthorize()
  public ResponseEntity<?> update(@RequestBody User user){
    try {
      return ResponseEntity.ok(userService.update(user));
    }catch (Exception e){
      log.warn(e.getMessage());
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/delete/{id}")
  //@PreAuthorize()
  public ResponseEntity<?> deleteById(@PathVariable Long id){
    try{
      userService.deleteUserById(id);
      return ResponseEntity.ok("Successfully deleted!");
    }catch (Exception e){
      e.printStackTrace();
      log.warn(e.getMessage());
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
