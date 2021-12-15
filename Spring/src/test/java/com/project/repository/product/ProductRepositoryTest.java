package com.project.repository.product;

import com.project.model.product.Category;
import com.project.model.product.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@DisplayName("<= ProductRepository Test =>")
class ProductRepositoryTest {
  private final CategoryRepository categoryRepository;
  private final ProductRepository productRepository;
  private Category category;
  private List<Product> products;


  @Autowired
  ProductRepositoryTest(CategoryRepository categoryRepository, ProductRepository productRepository) {
    this.categoryRepository = categoryRepository;
    this.productRepository = productRepository;
  }

  @BeforeEach
  void setUp() {
    category = categoryRepository.save(new Category(null, "Vegetables"));
    Category anotherCategory = categoryRepository.save(new Category(null, "Thing"));
    products = new ArrayList<>();
    products.add(productRepository.save(
        new Product(null, "Tomato1", category, List.of(), List.of(), 200D)
    ));
    products.add(productRepository.save(
        new Product(null, "Tomato2", category, List.of(), List.of(), 200D)
    ));
    products.add(productRepository.save(
        new Product(null, "Tomato3", category, List.of(), List.of(), 200D)
    ));
    products.add(productRepository.save(
        new Product(null, "Tomato4", category, List.of(), List.of(), 200D)
    ));
    productRepository.save(
        new Product(null, "Tomato5", anotherCategory, List.of(), List.of(), 200D)
    );
    productRepository.save(
        new Product(null, "Tomato6", anotherCategory, List.of(), List.of(), 200D)
    );
  }

  @AfterEach
  void tearDown() {
    productRepository.deleteAll();
    categoryRepository.deleteAll();
  }

  @Test
  @DisplayName("<= find all Products in category when exist =>")
  void findAllByCategoryId() {
    List<Product> productsInFirstCategory = productRepository.findAllByCategoryId(category.getId());

    assertAll(
        () -> assertThat(
            productsInFirstCategory
                .stream()
                .sorted(Comparator.comparingLong(Product::getId))
                .collect(Collectors.toList())
        ).isEqualTo(
            products
        ),
        () -> assertThat(
            productRepository.findAll().size()
        ).isEqualTo(6)
    );
  }

  @Test
  @DisplayName("<= empty list when id doesn't exist or there are no products in this category =>")
  void findEmptyListOfProduct(){
    Category tempCategory = categoryRepository.save(new Category(null, "Temp"));
    assertAll(
        () -> assertThat(
            productRepository
                .findAllByCategoryId(tempCategory.getId())
        ).isEmpty(),
        () -> assertThat(
            productRepository
                .findAllByCategoryId(10000L)
        ).isEmpty()
    );
  }
}