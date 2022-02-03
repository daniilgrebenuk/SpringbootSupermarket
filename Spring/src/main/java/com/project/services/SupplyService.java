package com.project.services;

import com.project.model.storage.Supply;

import java.time.LocalDate;
import java.util.List;

public interface SupplyService {

  Supply addSupply(Long storageId, LocalDate date);

  List<Supply> getAllSupplyByUsername(String username);

  List<Supply> getAllSupply();

  void addEmployeeToSupply(Long supplyId, Long userId);

  void addProductToSupply(Long supplyId, Long productId, Integer amount);

  void acceptSupplyById(Long supplyId);
}
