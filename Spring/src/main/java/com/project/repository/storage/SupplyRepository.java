package com.project.repository.storage;

import com.project.model.storage.Supply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SupplyRepository extends JpaRepository<Supply, Long> {

  String SQL_FOR_GET_ALL_BY_USERNAME =
      "SELECT s.* " +
      "FROM Supply s JOIN SupplyEmployee se ON s.id = se.supply_id " +
                    "JOIN Employee e ON e.id = se.employee_id " +
                    "JOIN User u ON e.user_id = u.id " +
      "WHERE u.username = ?1";


  @Query(value = SQL_FOR_GET_ALL_BY_USERNAME, nativeQuery = true)
  List<Supply> getAllByUsername(String username);
}
