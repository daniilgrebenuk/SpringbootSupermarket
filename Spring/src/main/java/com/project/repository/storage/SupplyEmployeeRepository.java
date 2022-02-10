package com.project.repository.storage;

import com.project.model.storage.SupplyEmployee;
import com.project.model.storage.SupplyEmployeeKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplyEmployeeRepository extends JpaRepository<SupplyEmployee, SupplyEmployeeKey> {

}
