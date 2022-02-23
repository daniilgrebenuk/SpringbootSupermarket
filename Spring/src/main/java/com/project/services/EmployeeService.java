package com.project.services;

import com.project.model.credential.Role;
import com.project.model.employee.Employee;

import java.util.List;

public interface EmployeeService {

  List<Employee> findAll();

  Employee update(Employee employee);

  Employee add(Employee employee);

  boolean delete(Long id);

  List<Role> roles();

  List<Employee> findAllBySupplyId(Long supplyId);
}
