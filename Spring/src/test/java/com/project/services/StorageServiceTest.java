package com.project.services;

import com.project.model.exception.DataNotFoundException;
import com.project.model.product.Product;
import com.project.model.product.ProductStorage;
import com.project.model.product.helper.ProductContainer;
import com.project.model.product.keys.ProductStorageKey;
import com.project.model.storage.Storage;
import com.project.repository.employee.EmployeeRepository;
import com.project.repository.product.ProductRepository;
import com.project.repository.product.ProductStorageRepository;
import com.project.repository.product.ProductSupplyRepository;
import com.project.repository.storage.StorageRepository;
import com.project.repository.storage.SupplyEmployeeRepository;
import com.project.repository.storage.SupplyRepository;
import com.project.services.implementation.CompletedStorageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("<= ProductService Test =>")
public class StorageServiceTest {

  private StorageService storageService;
  private long counter;

  @Mock
  private StorageRepository storageRepository;

  @Mock
  private ProductStorageRepository productStorageRepository;

  @Mock
  private SupplyEmployeeRepository supplyEmployeeRepository;

  /**
   * <h2>If you have changed the implementation, then change the init() code block</h2>
   */
  @BeforeEach
  void init(
      @Mock SupplyRepository supplyRepository,
      @Mock EmployeeRepository employeeRepository,
      @Mock ProductRepository productRepository,
      @Mock SupplyEmployeeRepository supplyEmployeeRepository,
      @Mock ProductSupplyRepository productSupplyRepository
      ) {
    storageService = new CompletedStorageServiceImpl(
        storageRepository,
        supplyRepository,
        employeeRepository,
        productRepository,
        supplyEmployeeRepository,
        productStorageRepository,
        productSupplyRepository
    );

    counter = 0;
  }

  @Test
  @DisplayName("<= find all storage =>")
  void getAllStorage() {
    List<Storage> storageList = IntStream.range(0, 15)
        .mapToObj(n -> initStorage())
        .collect(Collectors.toList());

    when(storageRepository.findAll()).thenReturn(storageList);

    assertThat(storageService.getAllStorage()).isEqualTo(storageList);
  }

  @Test
  @DisplayName("<= find storage by Id, if storage exists =>")
  void findStorageByIdWhenExists() {
    Storage storage = initStorage();
    long storageId = storage.getId();

    when(storageRepository.findById(any(Long.class))).thenReturn(Optional.of(storage));

    assertThat(storageService.getStorageById(storageId)).isEqualTo(storage);
  }

  @Test
  @DisplayName("<= throw exception when storage with current id doesn't exist =>")
  void findStorageByIdWhenDoesntExist() {
    long storageId = 1L;

    when(storageRepository.findById(any(Long.class))).thenReturn(Optional.empty());

    assertThatThrownBy(() -> storageService.getStorageById(storageId))
        .isInstanceOf(DataNotFoundException.class);
  }

  @Test
  @DisplayName("<= add Storage =>")
  void addStorage() {
    Storage storage = new Storage();

    when(storageRepository.save(any(Storage.class))).thenReturn(storage);

    assertThat(storageService.addStorage(new Storage())).isEqualTo(storage);
  }

  @Test
  @DisplayName("<= add Product to Storage")
  void addProductToStorage() {
    Storage storage = initStorage();
    ProductContainer product = initProductStorage(storage);
    storage.addProduct((ProductStorage) product);

    Storage toReturn = initStorage();
    toReturn.setId(storage.getId());


    when(productStorageRepository.save(any(ProductStorage.class))).thenReturn((ProductStorage) product);
    when(storageRepository.findById(any(Long.class))).thenReturn(Optional.of(toReturn));

    Storage toTest = storageService.addProductToStorage(toReturn.getId(), product);
    assertAll(
        () -> assertThat(toTest).isEqualTo(storage),
        () -> assertThat(toTest.getProducts()).isEqualTo(List.of((ProductStorage) product))
    );
  }

  @Test
  @DisplayName("<= add all Product to Storage =>")
  void addAllProductToStorage() {
    Storage storage = initStorage();

    ProductContainer product = initProductStorage(storage);
    List<ProductContainer> productContainerList = IntStream.range(0, 15)
        .mapToObj(n -> product)
        .collect(Collectors.toList());

    productContainerList.forEach(p -> storage.addProduct((ProductStorage) p));

    Storage toReturn = initStorage();
    toReturn.setId(storage.getId());


    when(productStorageRepository.save(any(ProductStorage.class))).thenReturn((ProductStorage) product);
    when(storageRepository.findById(any(Long.class))).thenReturn(Optional.of(toReturn));

    Storage toTest = storageService.addAllProductToStorage(toReturn.getId(), productContainerList);

    List<ProductStorage> toCheck = productContainerList.stream()
        .map(pc -> (ProductStorage) pc)
        .collect(Collectors.toList());

    assertAll(
        () -> assertThat(toTest).isEqualTo(storage),
        () -> assertThat(toTest.getProducts()).isEqualTo(toCheck)
    );
  }

  @Test
  @DisplayName("<= delete Storage by id =>")
  void deleteStorageById(){
    Long storageId = 1L;
    storageService.deleteStorageById(storageId);
    Mockito.verify(storageRepository).deleteById(storageId);
  }


  private Storage initStorage() {
    Storage storage = new Storage();
    storage.setId(counter++);
    storage.setLocation("" + counter);
    storage.setProducts(new ArrayList<>());
    return storage;
  }

  private ProductContainer initProductStorage(Storage storage) {
    Product product = initProduct();

    ProductStorageKey key = new ProductStorageKey();
    key.setStorageId(storage.getId());
    key.setProductId(product.getId());

    ProductStorage productStorage = new ProductStorage();
    productStorage.setStorage(storage);
    productStorage.setProduct(product);
    productStorage.setAmount((int) (Math.random() * 10) + 1);
    productStorage.setId(key);
    return productStorage;
  }

  private Product initProduct() {
    Product product = new Product();
    product.setId(counter++);
    return product;
  }
}
