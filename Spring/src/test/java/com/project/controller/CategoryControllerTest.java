package com.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.model.product.Category;
import com.project.model.product.Product;
import com.project.services.implementation.CategoryServiceImpl;
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

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DisplayName("<= CategoryController Test =>")
class CategoryControllerTest {
  private static final String DEFAULT_URI = "/api/category";

  @InjectMocks
  private CategoryController controller;

  @Mock
  private CategoryServiceImpl service;

  private MockMvc mockMvc;
  private final ObjectMapper mapper = new ObjectMapper();
  private Category correctCategory;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    correctCategory = new Category(1L, "Vegetables");
  }

  @Test
  @DisplayName("<= HttpStatus.OK when get all Categories =>")
  void getAllCategories() throws Exception{
    List<Category> categories = List.of(
        new Category(1L, "123"),
        new Category(2L, "123"),
        new Category(3L, "123"),
        new Category(4L, "123")
    );
    when(service.findAll()).thenReturn(categories);

    mockMvc
        .perform(get(DEFAULT_URI + "/all"))
        .andDo(print())
        .andExpectAll(
            status().isOk(),
            content().string(containsString(mapper.writeValueAsString(categories)))
        );
  }

  @Test
  @DisplayName("<= HttpStatus.CREATED when add correct Category =>")
  void createCorrectCategory() throws Exception {
    String categoryForRequest = mapper.writeValueAsString(correctCategory);
    correctCategory.setId(1L);
    when(service.add(any(Category.class))).thenReturn(correctCategory);

    mockMvc
        .perform(
            post(DEFAULT_URI + "/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(categoryForRequest)
        )
        .andDo(print())
        .andExpectAll(
            status().isCreated(),
            content().string(containsString(mapper.writeValueAsString(correctCategory)))
        );
  }

  @Test
  @DisplayName("<= HttpStatus.BAD_REQUEST and List<error.message> when add not valid Category =>")
  void createNotValidCategory() throws Exception {
    mockMvc
        .perform(
            post(DEFAULT_URI + "/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new Product()))
        )
        .andDo(print())
        .andExpectAll(
            status().isBadRequest(),
            content().string(containsString("\"name\":\"The category must have a name!\""))
        );
  }

  @Test
  @DisplayName("<= HttpStatus.OK when update correct Category =>")
  void updateCorrectCategory() throws Exception {
    correctCategory.setId(1L);

    when(service.update(any(Category.class))).thenReturn(correctCategory);
    mockMvc
        .perform(
            put(DEFAULT_URI + "/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(correctCategory))
        )
        .andDo(print())
        .andExpectAll(
            status().isOk(),
            content().string(containsString(mapper.writeValueAsString(correctCategory)))
        );
  }

  @Test
  @DisplayName("<= HttpStatus.BAD_REQUEST when update not valid Category =>")
  void updateNotValidCategory() throws Exception {
    mockMvc
        .perform(
            put(DEFAULT_URI + "/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new Category()))
        )
        .andDo(print())
        .andExpectAll(
            status().isBadRequest(),
            content().string(containsString("\"name\":\"The category must have a name!\""))
        );
  }

  @Test
  @DisplayName("<= HttpStatus.BAD_REQUEST when update Category without Id =>")
  void updateCategoryWithoutId() throws Exception {
    when(service.update(any(Category.class)))
        .thenThrow(new IllegalArgumentException("Unable to save Category without Id"));
    mockMvc
        .perform(
            put(DEFAULT_URI + "/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(correctCategory))
        )
        .andDo(print())
        .andExpectAll(
            status().isBadRequest(),
            content().string(containsString("Unable to save Category without Id"))
        );
  }
}