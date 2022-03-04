package com.project.services;

import com.project.model.storage.Supply;

import java.time.LocalDate;
import java.util.List;

public interface SupplyService {

  Supply addSupply(Long storageId, LocalDate date);

  List<Supply> getAllSupplyByUsername(String username);

  List<Supply> getAllSupply();

  Supply addEmployeeToSupply(Long supplyId, Long userId);

  Supply addProductToSupply(Long supplyId, Long productId, Integer amount);

  boolean acceptSupplyById(Long supplyId);

  List<Supply> findByStorageId(Long id);

  Supply findBySupplyId(Long id);

  void deleteSupplyById(Long id);
}
