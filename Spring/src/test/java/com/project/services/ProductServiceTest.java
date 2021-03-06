package com.project.services;

import com.project.model.exception.DataNotFoundException;
import com.project.model.product.Category;
import com.project.model.product.Product;
import com.project.model.product.helper.ProductResponse;
import com.project.repository.product.ProductRepository;
import com.project.services.implementation.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("<= ProductService Test =>")
class ProductServiceTest {

  private long counter;

  private ProductService productService;

  @Mock
  private ProductRepository productRepository;

  /**
   * <h2>If you have changed the implementation, then change the init() code block</h2>
   */
  @BeforeEach
  void init() {
    productService = new ProductServiceImpl(productRepository);
    counter = 1;
  }

  @Test
  @DisplayName("<= find Product with correct id =>")
  void findProductWithCorrectId() {
    Product product = initProduct(null);
    when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(product));

    assertAll(
        () -> assertThat(productService.findByProductId(1L)).isEqualTo(product),
        () -> assertDoesNotThrow(() -> productService.findByProductId(1L))
    );
  }

  @Test
  @DisplayName("<= find Product with incorrect id =>")
  void findProductWithIncorrectId() {
    when(productRepository.findById(any(Long.class))).thenReturn(Optional.empty());

    assertThatThrownBy(() -> productService.findByProductId(1L)).isInstanceOf(DataNotFoundException.class);
  }

  @Test
  @DisplayName("<= find all Products =>")
  void findAll() {
    Category category = new Category(1L, "Vegetables");
    Product product1 = initProduct(category);
    Product product2 = initProduct(category);
    Product product3 = initProduct(category);
    List<Product> products = Arrays.asList(product1, product2, product3);
    when(productRepository.findAll()).thenReturn(products);
    assertThat(productService.findAll()).isEqualTo(products);
  }

  @Test
  @DisplayName("<= add Product =>")
  void addProduct() {
    Product product = initProduct(null);
    when(productRepository.save(any(Product.class))).thenReturn(product);
    assertThat(productService.add(product)).isEqualTo(product);
  }

  @Test
  @DisplayName("<= update correct Product with Id =>")
  void updateCorrectProduct() {
    Product product = initProduct(null);

    when(productRepository.save(any(Product.class))).thenReturn(product);
    assertAll(
        () -> assertDoesNotThrow(() -> productService.update(product)),
        () -> assertThat(productService.update(product)).isEqualTo(product)
    );
  }

  @Test
  @DisplayName("<= update Product without Id =>")
  void updateProductWithoutId() {
    assertThrows(
        IllegalArgumentException.class,
        () -> productService.update(new Product())
    );
  }

  @Test
  @DisplayName("<= find all product by Category.id =>")
  void allProductByCategoryId() {
    Category category = new Category(1L, "Vegetables");
    Product product1 = initProduct(category);
    Product product2 = initProduct(category);
    Product product3 = initProduct(category);
    when(productRepository.findAllByCategoryId(any(Long.class))).thenReturn(Arrays.asList(product1, product2, product3));

    List<Product> products = productService.findAllByCategoryId(1L);
    assertAll(
        () -> assertThat(products.stream().filter(p -> p.getCategory().getId().equals(1L)).count()).isEqualTo(3),
        () -> assertThat(products.get(0)).isEqualTo(product1),
        () -> assertThat(products.get(1)).isEqualTo(product2),
        () -> assertThat(products.get(2)).isEqualTo(product3)
    );
  }

  @Test
  @DisplayName("<= allProductByCategoryId when there are no products in this category =>")
  void allProductByCategoryIdWithoutProducts() {
    when(productRepository.findAllByCategoryId(any(Long.class))).thenReturn(List.of());

    assertThrows(
        DataNotFoundException.class,
        () -> productService.findAllByCategoryId(1L)
    );
  }

  @Test
  @DisplayName("<= delete Product with correct Id")
  void deleteProductWithCorrectId() {
    Product product = initProduct(null);
    when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(product));

    assertAll(
        () -> assertDoesNotThrow(() -> productService.deleteById(1L)),
        () -> assertThat(productService.deleteById(1L)).isTrue()
    );
  }

  @Test
  @DisplayName("<= delete Product that doesn't exist =>")
  void deleteProductThatDoesntExist() {
    when(productRepository.findById(any(Long.class))).thenReturn(Optional.empty());

    assertThatThrownBy(() -> productService.deleteById(1L)).isInstanceOf(DataNotFoundException.class);
  }

  @Test
  @DisplayName("<= find all product by supplyId =>")
  void findAllBySupplyId(){
    Long supplyId = 1L;
    List<ProductResponse> products = IntStream
        .range(0,15)
        .mapToObj(n->new ProductResponse() {
          @Override
          public Long getId() {
            return (long)(Math.random()*20);
          }

          @Override
          public String getName() {
            return null;
          }

          @Override
          public Integer getAmount() {
            return (int) (Math.random()*20);
          }
        })
        .collect(Collectors.toList());

    when(productRepository.findAllBySupplyId(any(Long.class))).thenReturn(products);

    assertThat(productService.findAllBySupplyId(supplyId)).isEqualTo(products);
  }

  @Test
  @DisplayName("<= find all product by storageId =>")
  void findAllByStorageId(){
    Long storageId = 1L;
    List<Product> products = IntStream
        .range(0,15)
        .mapToObj(n->initProduct(null))
        .collect(Collectors.toList());

    when(productRepository.findAllByStorageId(any(Long.class))).thenReturn(products);

    assertThat(productService.findAllByStorageId(storageId)).isEqualTo(products);
  }

  @Test
  @DisplayName("<= find all by Order id =>")
  void findAllByOrderId(){
    Long orderId = 1L;
    List<Product> products = IntStream
        .range(0,15)
        .mapToObj(n->initProduct(null))
        .collect(Collectors.toList());

    when(productRepository.findAllByOrderId(any(Long.class))).thenReturn(products);

    assertThat(productService.findAllByOrderId(orderId)).isEqualTo(products);
  }

  private Product initProduct(Category category) {
    return new Product(
        counter++,
        "" + counter,
        category,
        null,
        null,
        null,
        (double) counter);
  }
}