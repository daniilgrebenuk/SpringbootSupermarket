package com.project.services;

import com.project.model.storage.Supply;
import com.project.repository.storage.StorageRepository;
import com.project.repository.storage.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorageService {
  private final StorageRepository storageRepository;
  private final SupplyRepository supplyRepository;

  @Autowired
  public StorageService(StorageRepository storageRepository, SupplyRepository supplyRepository) {
    this.storageRepository = storageRepository;
    this.supplyRepository = supplyRepository;
  }

  public Supply createNewSupply(Supply supply){
    return supplyRepository.save(supply);
  }
}
