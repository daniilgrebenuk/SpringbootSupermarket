package com.project.services;

import com.project.model.credential.User;
import com.project.model.employee.Employee;
import com.project.repository.credential.UserRepository;
import com.project.repository.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

@Service
public class EmployeeService {
  private final EmployeeRepository employeeRepository;
  private final UserRepository userRepository;

  @Autowired
  public EmployeeService(EmployeeRepository employeeRepository, UserRepository userRepository) {
    this.employeeRepository = employeeRepository;
    this.userRepository = userRepository;
  }


  public List<Employee> findAll(){
    return employeeRepository.findAll();
  }

  public Employee addEmployee(Employee employee) throws UserPrincipalNotFoundException {
    Long userId = employee.getUser().getId();
    User user = userRepository
        .findById(userId)
        .orElseThrow(
            ()->new UserPrincipalNotFoundException("User with id: " + userId)
        );
    employee.setUser(user);
    return employeeRepository.save(employee);
  }
}
