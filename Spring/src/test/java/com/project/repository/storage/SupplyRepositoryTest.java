package com.project.repository.storage;

import com.project.model.credential.Authority;
import com.project.model.credential.Role;
import com.project.model.credential.User;
import com.project.model.employee.Employee;
import com.project.model.storage.Storage;
import com.project.model.storage.Supply;
import com.project.model.storage.SupplyEmployee;
import com.project.repository.credential.RoleRepository;
import com.project.repository.credential.UserRepository;
import com.project.repository.employee.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@DisplayName("<= SupplyRepository Test =>")
public class SupplyRepositoryTest {

  private final SupplyRepository supplyRepository;
  private final SupplyEmployeeRepository supplyEmployeeRepository;
  private final EmployeeRepository employeeRepository;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final StorageRepository storageRepository;

  private final static String USERNAME_1 = "user1";
  private final static String USERNAME_2 = "user2";

  private List<Supply> firstUserSupply;
  private List<Supply> secondUserSupply;


  @Autowired
  public SupplyRepositoryTest(SupplyRepository supplyRepository, SupplyEmployeeRepository supplyEmployeeRepository, EmployeeRepository employeeRepository, UserRepository userRepository, RoleRepository roleRepository, StorageRepository storageRepository) {
    this.supplyRepository = supplyRepository;
    this.supplyEmployeeRepository = supplyEmployeeRepository;
    this.employeeRepository = employeeRepository;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.storageRepository = storageRepository;
  }

  @BeforeEach
  void setUp() {
    firstUserSupply = new ArrayList<>();
    secondUserSupply = new ArrayList<>();
    Role role = roleRepository.save(new Role(null, Authority.ADMIN));
    Storage storage = storageRepository.save(new Storage());

    User user1 = setUpUser(role, USERNAME_1);
    User user2 = setUpUser(role, USERNAME_2);

    Employee employee1 = setUpEmployee(user1);
    Employee employee2 = setUpEmployee(user2);

    Supply supply1 = setUpSupply(storage);
    Supply supply2 = setUpSupply(storage);
    Supply supply3 = setUpSupply(storage);

    firstUserSupply.addAll(List.of(supply1,supply2));
    secondUserSupply.add(supply3);

    addEmployeeToSupply(employee1, supply1);
    addEmployeeToSupply(employee1, supply2);
    addEmployeeToSupply(employee2, supply3);
  }

  @Test
  @DisplayName("<= find all supply by username =>")
  void findAllSupplyByUsername() {
    List<Supply> suppliesFromUser1 = supplyRepository.getAllByUsername(USERNAME_1);
    List<Supply> suppliesFromUser2 = supplyRepository.getAllByUsername(USERNAME_2);
    assertAll(
        () -> assertThat(suppliesFromUser1).isEqualTo(firstUserSupply),
        () -> assertThat(suppliesFromUser2).isEqualTo(secondUserSupply)
    );
  }

  private User setUpUser(Role role, String username) {
    User user = new User();
    user.setUsername(username);
    user.setPassword("1");
    user.setIsNonExpired(true);
    user.setRole(role);
    return userRepository.save(user);
  }

  private Employee setUpEmployee(User user) {
    return employeeRepository.save(
        new Employee(null, "Bob2", "Bob", 213L, "123123", "123213", 11313, LocalDate.now(), 2, user)
    );
  }

  private Supply setUpSupply(Storage storage) {
    Supply supply = new Supply();
    supply.setDate(LocalDate.now());
    supply.setStorage(storage);
    return supplyRepository.save(supply);
  }

  private void addEmployeeToSupply(Employee employee, Supply supply) {
    supplyEmployeeRepository.save(new SupplyEmployee(supply, employee));
  }
}
