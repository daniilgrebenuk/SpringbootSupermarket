package com.project.services.implementation;

import com.project.model.credential.Role;
import com.project.model.credential.User;
import com.project.model.employee.Employee;
import com.project.model.exception.DataNotFoundException;
import com.project.repository.employee.EmployeeRepository;
import com.project.services.EmployeeService;
import com.project.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeRepository employeeRepository;
  private final UserService userService;


  public List<Employee> findAll() {
    return employeeRepository.findAll();
  }

  public Employee update(Employee employee) {
    if (employee.getId() == null) {
      throw new IllegalArgumentException("Unable to save employee without id");
    }
    return saveWithEmployeeId(employee, employee.getId());
  }

  public Employee add(Employee employee) {
    if (employee.getId() != null) {
      throw new IllegalArgumentException("Unable to add employee with existing id");
    }
    return saveWithEmployeeId(employee, null);
  }

  private Employee saveWithEmployeeId(Employee employee, Long employeeId) {
    employee.setId(employeeId);
    User user = userService.findUserById(
        employee.getUser().getId()
    );
    user.setRole(
        employee.getUser().getRole()
    );

    user = userService.save(user);

    employee.setUser(user);
    return employeeRepository.save(employee);
  }

  public boolean delete(Long id) {
    employeeRepository.findById(id).orElseThrow(
        () -> new DataNotFoundException("Employee with id " + id + " doesn't exist!")
    );
    employeeRepository.deleteById(id);
    return true;
  }

  public List<Role> roles() {
    return userService.findAllRoles();
  }

  @Override
  public List<Employee> findAllBySupplyId(Long supplyId) {
    return employeeRepository.findAllBySupplyId(supplyId);
  }
}
