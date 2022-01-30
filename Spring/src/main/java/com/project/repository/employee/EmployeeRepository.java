package com.project.repository.employee;

import com.project.model.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  @Modifying
  @Query("DELETE FROM Employee e WHERE e.id = ?1")
  void deleteById(Long id);

  Optional<Employee> findByUserId(Long userId);
}
