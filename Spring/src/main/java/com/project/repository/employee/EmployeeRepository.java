package com.project.repository.employee;

import com.project.model.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  String SQL_ALL_EMPLOYEE_BY_SUPPLY_ID =
      "SELECT e.* " +
      "FROM employee e JOIN supply_employee se ON e.id = se.employee_id " +
      "WHERE se.supply_id = ?1";

  @Modifying
  @Query("DELETE FROM Employee e WHERE e.id = ?1")
  void deleteById(Long id);

  Optional<Employee> findByUserId(Long userId);

  @Query(value = SQL_ALL_EMPLOYEE_BY_SUPPLY_ID, nativeQuery = true)
  List<Employee> findAllBySupplyId(Long supplyId);
}
