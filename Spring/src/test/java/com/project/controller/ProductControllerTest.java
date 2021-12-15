package com.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.model.exception.DataNotFoundException;
import com.project.model.product.Category;
import com.project.model.product.Product;
import com.project.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DisplayName("<= ProductController Test =>")
class ProductControllerTest {
  private static final String defaultUri = "/api/product";

  @InjectMocks
  private ProductController controller;

  @Mock
  private ProductService service;

  private MockMvc mockMvc;
  private final ObjectMapper mapper = new ObjectMapper();
  private Product correctProduct;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();


    Category category = new Category(1L, "Vegetables");
    correctProduct = new Product(null, "Tomato", category, List.of(), List.of(), 200D);
  }

  @Test
  @DisplayName("<= HttpStatus.OK and correct data when use allProduct =>")
  void allProduct() throws Exception {
    Category category = new Category(1L, "Vegetables");
    Product product1 = new Product(1L, "Tomato1", category, List.of(), List.of(), 200D);
    Product product2 = new Product(2L, "Tomato2", category, List.of(), List.of(), 200D);
    Product product3 = new Product(3L, "Tomato3", category, List.of(), List.of(), 200D);

    when(service.findAll()).thenReturn(Arrays.asList(product1, product2, product3));

    mockMvc
        .perform(get(defaultUri + "/all"))
        .andDo(print())
        .andExpectAll(
            status().isOk(),
            content().string(containsString(mapper.writeValueAsString(product1))),
            content().string(containsString(mapper.writeValueAsString(product2))),
            content().string(containsString(mapper.writeValueAsString(product3)))
        );
  }

  @Test
  @DisplayName("<= HttpStatus.OK and correct data when use allProductByCategoryId with correct id =>")
  void allProductByCategoryId() throws Exception {
    Category category = new Category(1L, "Vegetables");
    Category categoryForAnotherProduct = new Category(2L, "Thing");
    Product product1 = new Product(1L, "Tomato1", category, List.of(), List.of(), 200D);
    Product product2 = new Product(2L, "Tomato2", category, List.of(), List.of(), 200D);
    Product productWithAnotherCategory = new Product(3L, "Tomato3", categoryForAnotherProduct, List.of(), List.of(), 200D);

    when(service.findAllByCategoryId(any(Long.class))).thenReturn(Arrays.asList(product1, product2));

    mockMvc
        .perform(get(defaultUri + "/all/category/1"))
        .andDo(print())
        .andExpectAll(
            status().isOk(),
            content().string(containsString(mapper.writeValueAsString(product1))),
            content().string(containsString(mapper.writeValueAsString(product2))),
            content().string(not(containsString(mapper.writeValueAsString(productWithAnotherCategory))))
        );
  }

  @Test
  @DisplayName("<= HttpStatus.OK and correct data when use allProductByCategoryId with correct id =>")
  void allProductByCategoryIdWithIncorrectId() throws Exception {
    when(service.findAllByCategoryId(any(Long.class))).thenThrow(new DataNotFoundException("Category is empty or does not exist"));

    mockMvc
        .perform(get(defaultUri + "/all/category/1"))
        .andDo(print())
        .andExpectAll(
            status().isBadRequest(),
            content().string(containsString("Category is empty or does not exist"))
        );
  }

  @Test
  @DisplayName("<= HttpStatus.CREATED when add correct Product =>")
  void createCorrectProduct() throws Exception {
    String productForRequest = mapper.writeValueAsString(correctProduct);
    correctProduct.setId(1L);
    when(service.add(any(Product.class))).thenReturn(correctProduct);

    mockMvc
        .perform(
            post(defaultUri + "/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productForRequest)
        )
        .andDo(print())
        .andExpectAll(
            status().isCreated(),
            content().string(containsString(mapper.writeValueAsString(correctProduct)))
        );
  }

  @Test
  @DisplayName("<= HttpStatus.BAD_REQUEST and List<error.message> when add not valid Product =>")
  void createNotValidProduct() throws Exception {
    mockMvc
        .perform(
            post(defaultUri + "/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new Product()))
        )
        .andDo(print())
        .andExpectAll(
            status().isBadRequest(),
            content().string(containsString("\"name\":\"The product must have a name!\"")),
            content().string(containsString("\"category\":\"The product must have a category!\"")),
            content().string(containsString("\"price\":\"The product must have a price!\""))
        );
  }

  @Test
  @DisplayName("<= HttpStatus.OK when update correct Product =>")
  void updateCorrectProduct() throws Exception {
    correctProduct.setId(1L);

    when(service.update(any(Product.class))).thenReturn(correctProduct);
    mockMvc
        .perform(
            put(defaultUri + "/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(correctProduct))
        )
        .andDo(print())
        .andExpectAll(
            status().isOk(),
            content().string(containsString(mapper.writeValueAsString(correctProduct)))
        );
  }

  @Test
  @DisplayName("<= HttpStatus.BAD_REQUEST when update not valid Product =>")
  void updateNotValidProduct() throws Exception {
    mockMvc
        .perform(
            put(defaultUri + "/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new Product()))
        )
        .andDo(print())
        .andExpectAll(
            status().isBadRequest(),
            content().string(containsString("\"name\":\"The product must have a name!\"")),
            content().string(containsString("\"category\":\"The product must have a category!\"")),
            content().string(containsString("\"price\":\"The product must have a price!\""))
        );
  }

  @Test
  @DisplayName("<= HttpStatus.BAD_REQUEST when update Product without Id =>")
  void updateProductWithoutId() throws Exception {
    when(service.update(any(Product.class)))
        .thenThrow(new IllegalArgumentException("Unable to save Product without Id"));
    mockMvc
        .perform(
            put(defaultUri + "/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(correctProduct))
        )
        .andDo(print())
        .andExpectAll(
            status().isBadRequest(),
            content().string(containsString("Unable to save Product without Id"))
        );
  }

}