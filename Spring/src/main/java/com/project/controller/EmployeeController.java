package com.project.controller;

import com.project.model.credential.Role;
import com.project.model.employee.Employee;
import com.project.model.exception.DataNotFoundException;
import com.project.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

  private final EmployeeService employeeService;

  @GetMapping("/all")
  //@PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<List<Employee>> getAllEmployee() {
    return ResponseEntity.ok(employeeService.findAll());
  }

  @PostMapping("/add")
  //@PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<?> addEmployee(@RequestBody @Valid Employee employee, Errors errors) {
    if (errors.hasErrors()) {
      return getResponseEntityWithErrors(errors);
    }

    try {
      employee = employeeService.add(employee);
    } catch (DataNotFoundException | IllegalArgumentException e) {
      log.warn("Warn: ", e);
      return ResponseEntity.badRequest().body(e.getMessage());
    }

    return new ResponseEntity<>(employee, HttpStatus.CREATED);
  }

  @PutMapping("/update")
  //@PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<?> updateEmployee(@RequestBody @Valid Employee employee, Errors errors) {
    if (errors.hasErrors()) {
      return getResponseEntityWithErrors(errors);
    }

    try {
      employee = employeeService.update(employee);
    } catch (DataNotFoundException | IllegalArgumentException e) {
      log.warn("Warn: ", e);
      return ResponseEntity.badRequest().body(e.getMessage());
    }

    return ResponseEntity.ok(employee);
  }

  @GetMapping("/all-roles")
  //@PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<List<Role>> getAllRoles() {
    return ResponseEntity.ok(employeeService.roles());
  }

  @DeleteMapping("/delete")
  //@PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<?> deleteEmployee(@RequestParam Long id) {
    try {
      employeeService.delete(id);
    } catch (DataNotFoundException e) {
      log.warn("", e);
      return ResponseEntity.badRequest().body(e.getMessage());
    }
    return ResponseEntity.ok().build();
  }

  private ResponseEntity<Map<String, String>> getResponseEntityWithErrors(Errors errors) {
    return new ResponseEntity<>(
        errors
            .getFieldErrors()
            .stream()
            .collect(Collectors.toMap(
                FieldError::getField,
                f -> Optional
                    .ofNullable(f.getDefaultMessage())
                    .orElse(f.getObjectName()))
            ),
        HttpStatus.BAD_REQUEST
    );
  }
}