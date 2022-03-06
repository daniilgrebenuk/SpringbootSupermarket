package com.project.services;

import com.project.model.employee.Employee;
import com.project.model.exception.DataNotFoundException;
import com.project.model.product.Product;
import com.project.model.product.ProductSupply;
import com.project.model.product.keys.ProductSupplyKey;
import com.project.model.storage.Storage;
import com.project.model.storage.Supply;
import com.project.model.storage.SupplyEmployee;
import com.project.model.storage.SupplyEmployeeKey;
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

import java.time.LocalDate;
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
@DisplayName("<= SupplyService Test =>")
public class SupplyServiceTest {
  private SupplyService supplyService;
  private long counter;

  @Mock
  private StorageRepository storageRepository;
  @Mock
  private SupplyRepository supplyRepository;
  @Mock
  private EmployeeRepository employeeRepository;
  @Mock
  private ProductRepository productRepository;
  @Mock
  private SupplyEmployeeRepository supplyEmployeeRepository;
  @Mock
  private ProductStorageRepository productStorageRepository;
  @Mock
  private ProductSupplyRepository productSupplyRepository;

  /**
   * <h2>If you have changed the implementation, then change the init() code block</h2>
   */
  @BeforeEach
  void init() {
    supplyService = new CompletedStorageServiceImpl(
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
  @DisplayName("<= add supply =>")
  void addSupply(){
    Storage storage = initStorage();
    Long storageId = storage.getId();

    LocalDate localDate = LocalDate.now();

    Supply supply = new Supply(storage, localDate);
    supply.setId(counter++);

    when(storageRepository.findById(any(Long.class))).thenReturn(Optional.of(storage));
    when(supplyRepository.save(any(Supply.class))).thenReturn(supply);

    assertThat(supplyService.addSupply(storageId, localDate)).isEqualTo(supply);
  }

  @Test
  @DisplayName("<= add supply when storage doesn't exists =>")
  void addSupplyWithException(){
    Long storageId = 1L;
    LocalDate localDate = LocalDate.now();
    when(storageRepository.findById(any(Long.class))).thenReturn(Optional.empty());
    assertThatThrownBy(()->supplyService.addSupply(storageId,localDate)).isInstanceOf(DataNotFoundException.class);
  }

  @Test
  @DisplayName("<= find all supply by employee username =>")
  void getAllSupplyByUsername(){
    String username = "username";
    Storage storage = initStorage();
    List<Supply> list = IntStream
        .range(0,15)
        .mapToObj(n-> initSupply(storage))
        .collect(Collectors.toList());

    when(supplyRepository.getAllByUsername(any(String.class))).thenReturn(list);

    assertThat(supplyService.getAllSupplyByUsername(username)).isEqualTo(list);
  }

  @Test
  @DisplayName("<= find all Supply =>")
  void getAllSupply(){
    Storage storage = initStorage();
    List<Supply> list = IntStream
        .range(0,15)
        .mapToObj(n-> initSupply(storage))
        .collect(Collectors.toList());

    when(supplyRepository.findAll()).thenReturn(list);

    assertThat(supplyService.getAllSupply()).isEqualTo(list);
  }

  @Test
  @DisplayName("<= add Employee to Supply =>")
  void addEmployeeToSupply(){
    Storage storage = initStorage();
    Supply supply = initSupply(storage);

    Employee employee = initEmployee();

    SupplyEmployee supplyEmployee = new SupplyEmployee(supply,employee);
    SupplyEmployeeKey key = new SupplyEmployeeKey();
    key.setSupplyId(supply.getId());
    key.setEmployeeId(employee.getId());
    supplyEmployee.setId(key);

    when(supplyRepository.findById(any(Long.class))).thenReturn(Optional.of(supply));
    when(employeeRepository.findById(any(Long.class))).thenReturn(Optional.of(employee));
    when(supplyEmployeeRepository.save(any(SupplyEmployee.class))).thenReturn(supplyEmployee);

    Supply toCheck = supplyService.addEmployeeToSupply(supply.getId(),employee.getId());
    assertAll(
        () -> assertThat(toCheck).isEqualTo(supply),
        () -> assertThat(toCheck.getEmployees()).isEqualTo(List.of(supplyEmployee))
    );
  }

  @Test
  @DisplayName("<= add Employee to Supply when Supply doesn't exist =>")
  void addEmployeeToSupplyWhenSupplyDoesntExist(){
    Long supplyId = 1L;
    Long employeeId = 1L;
    when(supplyRepository.findById(any(Long.class))).thenReturn(Optional.empty());
    assertThatThrownBy(
        ()->supplyService.addEmployeeToSupply(supplyId, employeeId)
    ).isInstanceOf(DataNotFoundException.class);
  }

  @Test
  @DisplayName("<= add Employee to Supply when Employee doesn't exist =>")
  void addEmployeeToSupplyWhenEmployeeDoesntExist(){
    Long supplyId = 1L;
    Long employeeId = 1L;
    when(supplyRepository.findById(any(Long.class))).thenReturn(Optional.of(initSupply(initStorage())));
    when(employeeRepository.findById(any(Long.class))).thenReturn(Optional.empty());
    assertThatThrownBy(
        ()->supplyService.addEmployeeToSupply(supplyId, employeeId)
    ).isInstanceOf(DataNotFoundException.class);
  }

  @Test
  @DisplayName("<= add Product to Supply =>")
  void addProductToSupply(){
    Storage storage = initStorage();
    Supply supply = initSupply(storage);

    Product product = initProduct();
    Integer amount = (int)(Math.random()*10)+2;
    ProductSupply productSupply = new ProductSupply();
    ProductSupplyKey key = new ProductSupplyKey();
    key.setSupplyId(supply.getId());
    key.setProductId(product.getId());
    productSupply.setId(key);
    productSupply.setProduct(product);
    productSupply.setSupplyProduct(supply);
    productSupply.setAmount(amount);


    when(supplyRepository.findById(any(Long.class))).thenReturn(Optional.of(supply));
    when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(product));
    when(productSupplyRepository.save(any(ProductSupply.class))).thenReturn(productSupply);

    Supply toTest = supplyService.addProductToSupply(supply.getId(),product.getId(), amount);

    assertAll(
        ()->assertThat(toTest).isEqualTo(supply),
        ()->assertThat(toTest.getProducts()).isEqualTo(List.of(productSupply))
    );
  }

  @Test
  @DisplayName("<= add Product to Supply when Supply doesn't exist =>")
  void addProductToSupplyWhenSupplyDoesntExist(){
    Long supplyId = 1L;
    Long productId = 1L;
    Integer amount = 1;
    when(supplyRepository.findById(any(Long.class))).thenReturn(Optional.empty());
    assertThatThrownBy(
        ()->supplyService.addProductToSupply(supplyId, productId,amount)
    ).isInstanceOf(DataNotFoundException.class);
  }

  @Test
  @DisplayName("<= add Product to Supply when Product doesn't exist =>")
  void addProductToSupplyWhenProductDoesntExist(){
    Long supplyId = 1L;
    Long productId = 1L;
    Integer amount = 1;
    when(supplyRepository.findById(any(Long.class))).thenReturn(Optional.of(initSupply(initStorage())));
    when(productRepository.findById(any(Long.class))).thenReturn(Optional.empty());
    assertThatThrownBy(
        ()->supplyService.addProductToSupply(supplyId, productId,amount)
    ).isInstanceOf(DataNotFoundException.class);
  }

  @Test
  @DisplayName("<= accept supply by supplyId =>")
  void acceptSupplyById(){
    Supply supply = initSupply(initStorage());

    when(supplyRepository.findById(any(Long.class))).thenReturn(Optional.of(supply));
    when(supplyRepository.save(any(Supply.class))).thenReturn(supply);

    assertThat(supplyService.acceptSupplyById(supply.getId())).isTrue();
  }

  @Test
  @DisplayName("<= accept supply by supplyId when supply have already accepted =>")
  void acceptSupplyByIdWhenAccepted(){
    Supply supply = initSupply(initStorage());
    supply.setAccepted(true);
    when(supplyRepository.findById(any(Long.class))).thenReturn(Optional.of(supply));

    assertThat(supplyService.acceptSupplyById(supply.getId())).isFalse();
  }

  @Test
  @DisplayName("<= delete Supply by id =>")
  void deleteSupplyById(){
    Long supplyId = 1L;
    supplyService.deleteSupplyById(supplyId);
    Mockito.verify(supplyRepository).deleteById(supplyId);
  }

  @Test
  @DisplayName("<= find Supply by Storage id =>")
  void findSupplyByStorageId(){
    Storage storage = initStorage();

    List<Supply> supplies = IntStream
        .range(0,15)
        .mapToObj(n->initSupply(storage))
        .collect(Collectors.toList());

    when(supplyRepository.findByStorageId(any(Long.class))).thenReturn(supplies);
    assertThat(supplyService.findByStorageId(storage.getId())).isEqualTo(supplies);
  }


  private Storage initStorage(){
    Storage storage = new Storage();
    storage.setId(counter++);
    storage.setLocation(""+counter);
    return storage;
  }

  private Product initProduct(){
    Product product = new Product();
    product.setId(counter++);
    product.setName(""+counter);
    product.setPrice((double)counter);
    return product;
  }

  private Supply initSupply(Storage storage){
    Supply supply = new Supply();
    supply.setId(counter++);
    supply.setDate(LocalDate.now());
    supply.setStorage(storage);
    supply.setEmployees(new ArrayList<>());
    supply.setProducts(new ArrayList<>());
    return supply;
  }

  private Employee initEmployee(){
    Employee employee = new Employee();
    employee.setId(counter++);
    employee.setName(""+counter);
    employee.setPesel(counter);
    employee.setImageUrl(""+counter);
    return employee;
  }
}
