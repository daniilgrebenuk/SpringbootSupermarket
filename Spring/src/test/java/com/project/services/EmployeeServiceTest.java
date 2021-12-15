package com.project.services;

import com.project.model.credential.Authority;
import com.project.model.credential.Role;
import com.project.model.credential.User;
import com.project.model.employee.Employee;
import com.project.model.exception.DataNotFoundException;
import com.project.repository.employee.EmployeeRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("<= EmployeeService Test =>")
class EmployeeServiceTest {

  @InjectMocks
  private EmployeeService employeeService;

  @Mock
  private EmployeeRepository employeeRepository;
  @Mock
  private UserService userService;

  @Test
  @DisplayName("<= find all employee when is not empty =>")
  void findAllEmployee() {
    Employee employee1 = new Employee(1L, "Bob", "Bob", 213L, "123123", "123213", 11313, LocalDate.now(), 2, null);
    Employee employee2 = new Employee(2L, "Bob", "Bob", 213L, "123123", "123213", 11313, LocalDate.now(), 2, null);
    Employee employee3 = new Employee(3L, "Bob", "Bob", 213L, "123123", "123213", 11313, LocalDate.now(), 2, null);


    when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1, employee2, employee3));

    assertAll(
        () -> assertThat(employeeService.findAll().size()).isEqualTo(3),
        () -> assertThat(employeeService.findAll().get(0)).isEqualTo(employee1),
        () -> assertThat(employeeService.findAll().get(1)).isEqualTo(employee2),
        () -> assertThat(employeeService.findAll().get(2)).isEqualTo(employee3)
    );
  }

  @Test
  @DisplayName("<= find all employee when is empty =>")
  void findAllEmployeeWhenIsEmpty() {
    when(employeeRepository.findAll()).thenReturn(new ArrayList<>());
    assertThat(employeeService.findAll()).isEmpty();
  }

  @Test
  @DisplayName("<= update employee with Id =>")
  void updateEmployee() {
    User user = new User();
    user.setId(1L);
    user.setRole(new Role(1L, Authority.USER));
    Employee returnEmployee = new Employee(1L, "Bob", "Bob", 213L, "123123", "123213", 11313, LocalDate.now(), 2, user);

    when(employeeRepository.save(any(Employee.class))).thenReturn(returnEmployee);
    when(userService.save(any(User.class))).thenReturn(user);
    when(userService.findUserById(any(Long.class))).thenReturn(user);

    assertThat(employeeService.update(returnEmployee)).isEqualTo(returnEmployee);
  }

  @Test
  @DisplayName("<= update employee without Id =>")
  void updateEmployeeWithoutId() {
    Employee forTest = new Employee(null, "Bob", "Bob", 213L, "123123", "123213", 11313, LocalDate.now(), 2, null);

    assertThatIllegalArgumentException().isThrownBy(() -> employeeService.update(forTest));
  }

  @Test
  @DisplayName("<= add employee without Id =>")
  void addEmployee() {
    User user = new User();
    user.setId(1L);
    user.setRole(new Role(1L, Authority.USER));
    Employee returnEmployee = new Employee(1L, "Bob", "Bob", 213L, "123123", "123213", 11313, LocalDate.now(), 2, user);

    when(employeeRepository.save(any(Employee.class))).thenReturn(returnEmployee);
    when(userService.save(any(User.class))).thenReturn(user);
    when(userService.findUserById(any(Long.class))).thenReturn(user);

    Employee forTest = new Employee(null, "Bob", "Bob", 213L, "123123", "123213", 11313, LocalDate.now(), 2, user);
    assertThat(employeeService.add(forTest)).isEqualTo(returnEmployee);
  }

  @Test
  @DisplayName("<= add employee with Id =>")
  void addEmployeeWithId() {
    Employee forTest = new Employee(1L, "Bob", "Bob", 213L, "123123", "123213", 11313, LocalDate.now(), 2, null);
    assertThatIllegalArgumentException().isThrownBy(() -> employeeService.add(forTest));

  }

  @Test
  @DisplayName("<= delete employee that exists =>")
  void deleteEmployeeThatExists() {
    Employee returnEmployee = new Employee(1L, "Bob", "Bob", 213L, "123123", "123213", 11313, LocalDate.now(), 2, null);
    when(employeeRepository.findById(any(Long.class))).thenReturn(Optional.of(returnEmployee));

    assertAll(
        () -> assertDoesNotThrow(() -> employeeService.delete(1L)),
        () -> assertThat(employeeService.delete(1L)).isTrue()
    );
  }

  @Test
  @DisplayName("<= delete employee that doesn't exist =>")
  void deleteEmployeeThatDoesNotExists() {
    when(employeeRepository.findById(any(Long.class))).thenReturn(Optional.empty());

    assertThatThrownBy(() -> employeeService.delete(1L)).isInstanceOf(DataNotFoundException.class);
  }
}