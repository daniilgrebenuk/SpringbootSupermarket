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
  private int counter;

  @Autowired
  public EmployeeRepositoryTest(EmployeeRepository employeeRepository, UserRepository userRepository, RoleRepository roleRepository) {
    this.employeeRepository = employeeRepository;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
  }

  @BeforeEach
  void setUp() {
    counter = 0;
    Role role = roleRepository.save(new Role(null, Authority.USER));
    User user = setUpUser(role);

    employee = setUpEmployee(user);
    setUpEmployee(user);
    setUpEmployee(user);
  }


  @Test
  @DisplayName("<= delete when employee exists =>")
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
    employeeRepository.deleteById(1000L);
    System.out.println(employeeRepository.findAll());
    assertAll(
        () -> assertThat(employeeRepository.findAll().size()).isEqualTo(3),
        () -> assertThat(
            employeeRepository.findAll().contains(employee)
        ).isTrue()
    );
  }

  private User setUpUser(Role role){
    User user = new User();
    user.setUsername("1");
    user.setPassword("1");
    user.setIsNonExpired(true);
    user.setRole(role);
    return userRepository.save(user);
  }

  private Employee setUpEmployee(User user){
    return employeeRepository.save(
        new Employee(
            null,
            "Bob" + (counter++),
            "Bob",
            213L,
            "123123",
            "123213",
            11313,
            LocalDate.now(),
            2, user
        )
    );
  }
}