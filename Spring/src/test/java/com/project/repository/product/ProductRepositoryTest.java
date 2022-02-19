package com.project.repository.product;

import com.project.model.product.Category;
import com.project.model.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@DisplayName("<= ProductRepository Test =>")
class ProductRepositoryTest {

  private final CategoryRepository categoryRepository;
  private final ProductRepository productRepository;
  private Category category;
  private List<Product> products;
  private int counter;


  @Autowired
  ProductRepositoryTest(CategoryRepository categoryRepository, ProductRepository productRepository) {
    this.categoryRepository = categoryRepository;
    this.productRepository = productRepository;
  }

  @BeforeEach
  void setUp() {
    counter = 0;
    category = setUpCategory("Category1");
    Category anotherCategory = setUpCategory("Another category");
    products = new ArrayList<>();
    products.add(setUpProduct(category));
    products.add(setUpProduct(category));
    products.add(setUpProduct(category));
    products.add(setUpProduct(category));
    products.add(setUpProduct(category));
    products.add(setUpProduct(category));

    setUpProduct(anotherCategory);
    setUpProduct(anotherCategory);
    setUpProduct(anotherCategory);
    setUpProduct(anotherCategory);
  }

  @Test
  @DisplayName("<= find all Products in category when exist =>")
  void findAllByCategoryId() {
    List<Product> productsInFirstCategory = productRepository.findAllByCategoryId(category.getId());

    assertAll(
        () -> assertThat(productsInFirstCategory).isEqualTo(products),
        () -> assertThat(productRepository.findAll().size()).isEqualTo(counter)
    );
  }

  @Test
  @DisplayName("<= empty list when id doesn't exist or there are no products in this category =>")
  void findEmptyListOfProduct() {
    Category tempCategory = setUpCategory("empty category");
    assertAll(
        () -> assertThat(productRepository.findAllByCategoryId(tempCategory.getId())).isEmpty(),
        () -> assertThat(productRepository.findAllByCategoryId(10000L)).isEmpty()
    );
  }

  Product setUpProduct(Category category) {
    return productRepository.save(new Product(null, "Tomato" + (counter++), category, List.of(), 200D));
  }

  Category setUpCategory(String name){
    return categoryRepository.save(new Category(null, name));
  }
}