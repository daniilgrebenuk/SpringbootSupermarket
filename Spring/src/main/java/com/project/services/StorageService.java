package com.project.services;

import com.project.model.product.helper.ProductContainer;
import com.project.model.storage.Storage;

import java.util.List;

public interface StorageService {

  Storage getStorageById(Long id);

  List<Storage> getAllStorage();

  Storage addStorage(Storage storage);

  Storage addProductToStorage(Long storageId, ProductContainer product);

  Storage addAllProductToStorage(Long storageId,  List<? extends ProductContainer> products);

  void deleteStorageById(Long id);
}
