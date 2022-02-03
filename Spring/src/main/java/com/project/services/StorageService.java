package com.project.services;

import com.project.model.product.Product;
import com.project.model.product.helper.ProductContainer;
import com.project.model.storage.Storage;

import java.util.List;

public interface StorageService {

  List<Storage> getAllStorage();

  Storage addStorage(Storage storage);

  Storage addAllProductToStorage(Long storageId,  List<? extends ProductContainer> products);
}
