package com.project.repository.employee;

import com.project.model.credential.Authority;
import com.project.model.credential.Role;
import com.project.model.credential.User;
import com.project.model.employee.Employee;
import com.project.repository.credential.RoleRepository;
import com.project.repository.credential.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@DisplayName("<= EmployeeRepository Test =>")
class EmployeeRepositoryTest {


  private final EmployeeRepository employeeRepository;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private Employee employee;

  @Autowired
  public EmployeeRepositoryTest(EmployeeRepository employeeRepository, UserRepository userRepository, RoleRepository roleRepository) {
    this.employeeRepository = employeeRepository;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
  }

  @BeforeEach
  void setUp() {
    Role role = new Role(1L, Authority.USER);
    role = roleRepository.save(role);
    User user = new User();
    user.setUsername("1");
    user.setPassword("1");
    user.setIsNonExpired(true);
    user.setRole(role);
    user.setId(1L);
    user = userRepository.save(user);

    employee = employeeRepository.saveAndFlush(new Employee(1L, "Bob1", "Bob", 213L, "123123", "123213", 11313, LocalDate.now(), 2, user));
    employeeRepository.saveAndFlush(new Employee(2L, "Bob2", "Bob", 213L, "123123", "123213", 11313, LocalDate.now(), 2, user));
    employeeRepository.saveAndFlush(new Employee(3L, "Bob3", "Bob", 213L, "123123", "123213", 11313, LocalDate.now(), 2, user));

  }


  @Test
  @DisplayName("<= delete nothing when employee exists =>")
  void deleteById() {

    employeeRepository.deleteById(employee.getId());
    System.out.println(employeeRepository.findAll());
    assertAll(
        () -> assertThat(employeeRepository.findAll().size()).isEqualTo(2),
        () -> assertThat(
            employeeRepository.findAll().stream().filter(e -> e.getId().equals(employee.getId())).findAny().isEmpty()
        ).isTrue()
    );
  }

  @Test
  @DisplayName("<= delete nothing when employee doesn't exist =>")
  void deleteNothingWithIncorrectId() {
    employeeRepository.deleteById(7L);

    assertAll(
        () -> assertThat(employeeRepository.findAll().size()).isEqualTo(3),
        () -> assertThat(
            employeeRepository.findAll().stream().filter(e -> e.getId().equals(4L)).findAny().isEmpty()
        ).isTrue()
    );
  }
}