package com.project.services;

import com.project.model.storage.Supply;

import java.util.List;

public interface StorageService {

  Supply createNewSupply(Supply supply);

  List<Supply> getAllSupplyByUsername(String username);

  List<Supply> getAllSupply();

  void addEmployeeToSupply(Long supplyId, Long userId);
}
