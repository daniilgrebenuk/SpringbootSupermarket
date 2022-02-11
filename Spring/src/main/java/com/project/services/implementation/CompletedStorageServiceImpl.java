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
import com.project.repository.product.ProductStorageRepository;
import com.project.repository.storage.StorageRepository;
import com.project.repository.storage.SupplyEmployeeRepository;
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
  private final SupplyEmployeeRepository supplyEmployeeRepository;
  private final ProductStorageRepository productStorageRepository;


  @Override
  public Supply addSupply(Long storageId, LocalDate date) {
    Storage storage = storageRepository
        .findById(storageId)
        .orElseThrow(() -> new DataNotFoundException("Storage with current id doesn't exist: " + storageId));

    Supply supply = new Supply(storage, date);
    return supplyRepository.save(supply);
  }

  @Override
  public List<Supply> getAllSupplyByUsername(String username) {
    return supplyRepository.getAllByUsername(username);
  }

  @Override
  public List<Supply> getAllSupply() {
    return supplyRepository.findAll();
  }

  @Override
  public void addEmployeeToSupply(Long supplyId, Long employeeId) {
    Supply supply = supplyRepository
        .findById(supplyId)
        .orElseThrow(() -> new DataNotFoundException("Supply with current id doesn't exist: " + supplyId));

    Employee employee = employeeRepository
        .findById(employeeId)
        .orElseThrow(() -> new DataNotFoundException("Employee with current id doesn't exist: " + employeeId));

    SupplyEmployee supplyEmployee = new SupplyEmployee(supply, employee);

    supplyEmployeeRepository.save(supplyEmployee);
  }

  @Override
  public void addProductToSupply(Long supplyId, Long productId, Integer amount) {
    Supply supply = supplyRepository
        .findById(supplyId)
        .orElseThrow(() -> new DataNotFoundException("Supply with current id doesn't exist: " + supplyId));

    Product product = productRepository
        .findById(productId)
        .orElseThrow(() -> new DataNotFoundException("Product with current id doesn't exist: " + productId));

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
        .orElseThrow(() -> new DataNotFoundException("Supply with current id doesn't exist: " + supplyId));
    supply.setAccepted(true);

    addAllProductToStorage(supply.getStorage(), supply.getProducts());
  }

  @Override
  public Storage getStorageById(Long id) {
    return storageRepository
        .findById(id)
        .orElseThrow(() -> new DataNotFoundException("Storage with current id doesn't exist: " + id));
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
  public Storage addProductToStorage(Long storageId, ProductContainer product) {
    Storage storage = getStorageById(storageId);

    ProductStorage productStorage = productContainerToProductStorage(storage,product);

    storage.addProduct(productStorageRepository.save(productStorage));

    return storage;
  }

  @Override
  public Storage addAllProductToStorage(Long storageId, List<? extends ProductContainer> products) {
    Storage storage = getStorageById(storageId);

    return addAllProductToStorage(storage, products);
  }

  private Storage addAllProductToStorage(Storage storage, List<? extends ProductContainer> products) {
    products
        .stream()
        .map(cont->productContainerToProductStorage(storage,cont))
        .forEach(ps -> storage.addProduct(productStorageRepository.save(ps)));
    return storage;
  }

  private ProductStorage productContainerToProductStorage(Storage storage, ProductContainer productContainer){
    ProductStorage productStorage = new ProductStorage();
    productStorage.setProduct(productContainer.getProduct());
    productStorage.setStorage(storage);
    productStorage.setAmount(productContainer.getAmount());
    return productStorage;
  }
}
