package com.project.services.implementation;

import com.project.model.employee.Employee;
import com.project.model.exception.DataNotFoundException;
import com.project.model.product.Product;
import com.project.model.product.ProductStorage;
import com.project.model.product.ProductSupply;
import com.project.model.product.helper.ProductContainer;
import com.project.model.storage.Storage;
import com.project.model.storage.Supply;
import com.project.model.storage.SupplyEmployee;
import com.project.repository.employee.EmployeeRepository;
import com.project.repository.product.ProductRepository;
import com.project.repository.storage.StorageRepository;
import com.project.repository.storage.SupplyRepository;
import com.project.services.StorageService;
import com.project.services.SupplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompletedStorageServiceImpl implements SupplyService, StorageService {
  private final StorageRepository storageRepository;
  private final SupplyRepository supplyRepository;
  private final EmployeeRepository employeeRepository;
  private final ProductRepository productRepository;



  public Supply addSupply(Long storageId, LocalDate date){
    Storage storage = storageRepository
        .findById(storageId)
        .orElseThrow(()->new DataNotFoundException("Storage with current id doesn't exist: " + storageId));

    Supply supply = new Supply(storage, date);
    return supplyRepository.save(supply);
  }

  public List<Supply> getAllSupplyByUsername(String username) {
    return supplyRepository.getAllByUsername(username);
  }

  public List<Supply> getAllSupply() {
    return supplyRepository.findAll();
  }

  public void addEmployeeToSupply(Long supplyId, Long userId) {
    Supply supply = supplyRepository
        .findById(supplyId)
        .orElseThrow(()->new DataNotFoundException("Supply with current id doesn't exist: " + supplyId));

    Employee employee = employeeRepository
        .findByUserId(userId)
        .orElseThrow(()->new DataNotFoundException("Employee with current id doesn't exist: " + userId));

    SupplyEmployee supplyEmployee = new SupplyEmployee();
    supplyEmployee.setEmployee(employee);
    supplyEmployee.setSupplyEmployee(supply);

    supply.addEmployee(supplyEmployee);
    supplyRepository.save(supply);
  }

  @Override
  public void addProductToSupply(Long supplyId, Long productId, Integer amount) {
    Supply supply = supplyRepository
        .findById(supplyId)
        .orElseThrow(()->new DataNotFoundException("Supply with current id doesn't exist: " + supplyId));

    Product product = productRepository
        .findById(productId)
        .orElseThrow(()->new DataNotFoundException("Product with current id doesn't exist: " + productId));

    ProductSupply productSupply = new ProductSupply();
    productSupply.setProduct(product);
    productSupply.setSupplyProduct(supply);
    productSupply.setAmount(amount);
    supply.addProduct(productSupply);

    supplyRepository.save(supply);
  }

  @Override
  public void acceptSupplyById(Long supplyId) {
    Supply supply = supplyRepository
        .findById(supplyId)
        .orElseThrow(()->new DataNotFoundException("Supply with current id doesn't exist: " + supplyId));
    supply.setAccepted(true);

    addAllProductToStorage(supply.getStorage(), supply.getProducts());
  }

  @Override
  public List<Storage> getAllStorage() {
    return storageRepository.findAll();
  }

  @Override
  public Storage addStorage(Storage storage) {
    return storageRepository.save(storage);
  }

  @Override
  public Storage addAllProductToStorage(Long storageId, List<? extends ProductContainer> products) {
    Storage storage = storageRepository
        .findById(storageId)
        .orElseThrow(()->new DataNotFoundException("Storage with current id doesn't exist: " + storageId));

    return addAllProductToStorage(storage, products);
  }

  private Storage addAllProductToStorage(Storage storage,  List<? extends ProductContainer> products){
    products
        .stream()
        .map(cont -> {
          ProductStorage productStorage = new ProductStorage();
          productStorage.setProduct(cont.getProduct());
          productStorage.setStorage(storage);
          productStorage.setAmount(cont.getAmount());
          return productStorage;
        })
        .forEach(storage::addProduct);
    return storage;
  }
}
