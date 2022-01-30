package com.project.services;

import com.project.model.employee.Employee;
import com.project.model.exception.DataNotFoundException;
import com.project.model.storage.Supply;
import com.project.model.storage.SupplyEmployee;
import com.project.repository.employee.EmployeeRepository;
import com.project.repository.storage.StorageRepository;
import com.project.repository.storage.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageService {
  private final StorageRepository storageRepository;
  private final SupplyRepository supplyRepository;
  private final EmployeeRepository employeeRepository;

  @Autowired
  public StorageService(StorageRepository storageRepository, SupplyRepository supplyRepository, EmployeeRepository employeeRepository) {
    this.storageRepository = storageRepository;
    this.supplyRepository = supplyRepository;
    this.employeeRepository = employeeRepository;
  }

  public Supply createNewSupply(Supply supply){
    supply.setAccepted(false);
    return supplyRepository.save(supply);
  }

  public List<Supply> getAllSupplyByUsername(String username) {
    return supplyRepository.getAllByUsername(username);
  }

  public void addEmployeeToSupply(Long supplyId, Long userId) {
    Supply supply = supplyRepository
        .findById(supplyId)
        .orElseThrow(()->new DataNotFoundException("Supply with current id doesn't exist!"));

    Employee employee = employeeRepository
        .findByUserId(userId)
        .orElseThrow(()->new DataNotFoundException("Employee with current user id doesn't exist!"));

    SupplyEmployee supplyEmployee = new SupplyEmployee();
    supplyEmployee.setEmployee(employee);
    supplyEmployee.setSupplyEmployee(supply);

    supply.addEmployee(supplyEmployee);
    supplyRepository.save(supply);
  }
}
