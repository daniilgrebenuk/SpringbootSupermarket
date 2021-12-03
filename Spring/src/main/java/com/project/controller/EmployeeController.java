package com.project.controller;

import com.project.model.employee.Employee;
import com.project.services.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employee")
@Slf4j
public class EmployeeController {

  private final EmployeeService employeeService;

  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @GetMapping("/all")
  //@PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<List<Employee>> getAllEmployee() {
    return ResponseEntity.ok(employeeService.findAll());
  }

  @PostMapping("/add")
  //@PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
    try {
      employee = employeeService.addEmployee(employee);
    } catch (UserPrincipalNotFoundException e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().build();
    }
    log.info(employee.toString());
    return ResponseEntity.ok(employee);
  }


}
