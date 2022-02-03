package com.project.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.model.credential.Authority;
import com.project.model.credential.Role;
import com.project.model.credential.User;
import com.project.model.employee.Employee;
import com.project.model.exception.DataNotFoundException;
import com.project.services.implementation.EmployeeServiceImpl;
import lombok.extern.slf4j.Slf4j;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.matchesRegex;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DisplayName("<= EmployeeController Test =>")
@Slf4j
class EmployeeControllerTest {
  private final static String DEFAULT_URI = "/api/employee";

  @InjectMocks
  private EmployeeController employeeController;

  @Mock
  private EmployeeServiceImpl employeeServiceImpl;

  private MockMvc mockMvc;
  private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
  private Employee correctEmployee;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    User user = new User();
    user.setId(1L);
    user.setRole(new Role(1L, Authority.USER));
    correctEmployee =
        new Employee(null, "Bob", "Bob", 213L, "123123", "123213", 11313, LocalDate.now(), 2, user);
  }

  @Test
  @DisplayName("<= HttpStatus.OK and correct data when use getAllEmployee =>")
  void getAllEmployee() throws Exception {
    Employee employee1 = new Employee(1L, "Bob", "Bob", 213L, "123123", "123213", 11313, LocalDate.now(), 2, null);
    Employee employee2 = new Employee(2L, "Bob", "Bob", 213L, "123123", "123213", 11313, LocalDate.now(), 2, null);
    Employee employee3 = new Employee(3L, "Bob", "Bob", 213L, "123123", "123213", 11313, LocalDate.now(), 2, null);
    List<Employee> employees = new ArrayList<>(
        Arrays.asList(employee1, employee2, employee3)
    );

    when(employeeServiceImpl.findAll()).thenReturn(employees);

    mockMvc
        .perform(get(DEFAULT_URI + "/all"))
        .andDo(print())
        .andExpectAll(
            status().isOk(),
            content().string(containsString(mapper.writeValueAsString(employee1))),
            content().string(containsString(mapper.writeValueAsString(employee2))),
            content().string(containsString(mapper.writeValueAsString(employee3)))
        );
  }

  @Test
  @DisplayName("<= HttpStatus.CREATED when add correct Employee =>")
  void addCorrectEmployee() throws Exception {
    String employeeForRequest = mapper.writeValueAsString(correctEmployee);
    correctEmployee.setId(1L);
    when(employeeServiceImpl.add(any(Employee.class))).thenReturn(correctEmployee);

    mockMvc
        .perform(
            post(DEFAULT_URI + "/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeForRequest)
        )
        .andDo(print())
        .andExpectAll(
            status().isCreated(),
            content().string(containsString(mapper.writeValueAsString(correctEmployee)))
        );
  }
  @Test
  @DisplayName("<= HttpStatus.BAD_REQUEST and List<error.message> when add not valid Employee =>")
  void addNotValidEmployee() throws Exception{
    mockMvc
        .perform(
            post(DEFAULT_URI + "/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new Employee()))
        )
        .andDo(print())
        .andExpectAll(
            status().isBadRequest(),
            content().string(containsString("\"name\":\"Enter name\"")),
            content().string(containsString("\"surname\":\"Enter surname\"")),
            content().string(containsString("\"phone\":\"Enter phone\"")),
            content().string(containsString("\"salary\":\"Enter salary\"")),
            content().string(containsString("\"enrollmentDate\":\"Enter enrollment date\"")),
            content().string(containsString("\"contractInMonth\":\"Enter the duration of the contract in months\"")),
            content().string(containsString("\"user\":\"Enter userId and Role\""))
        );
  }

  @Test
  @DisplayName("<= HttpStatus.BAD_REQUEST when add Employee with id =>")
  void addEmployeeWithId() throws Exception {
    when(employeeServiceImpl.add(any(Employee.class))).thenThrow(new IllegalArgumentException("Unable to add employee with existing id"));

    mockMvc
        .perform(
            post(DEFAULT_URI + "/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(correctEmployee))
        )
        .andDo(print())
        .andExpectAll(
            status().isBadRequest(),
            content().string(containsString("Unable to add employee with existing id"))
        );
  }

  @Test
  @DisplayName("<= HttpStatus.BAD_REQUEST when add Employee without User =>")
  void addEmployeeWithoutUserOrWithIncorrectUserId() throws Exception {
    when(employeeServiceImpl.add(any(Employee.class))).thenThrow(new DataNotFoundException("User with id: 11 doesn't exist!"));

    mockMvc
        .perform(
            post(DEFAULT_URI + "/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(correctEmployee))
        )
        .andDo(print())
        .andExpectAll(
            status().isBadRequest(),
            content().string(matchesRegex("User with id: \\d+ doesn't exist!"))
        );
  }


  @Test
  @DisplayName("<= HttpStatus.OK when update correct Employee =>")
  void updateCorrectEmployee() throws Exception {
    correctEmployee.setId(1L);

    when(employeeServiceImpl.update(any(Employee.class))).thenReturn(correctEmployee);
    mockMvc
        .perform(
            put(DEFAULT_URI + "/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(correctEmployee))
        )
        .andDo(print())
        .andExpectAll(
            status().isOk(),
            content().string(containsString(mapper.writeValueAsString(correctEmployee)))
        );
  }

  @Test
  @DisplayName("<= HttpStatus.BAD_REQUEST when update Employee without Id =>")
  void updateEmployeeWithoutId() throws Exception {

    when(employeeServiceImpl.update(any(Employee.class))).thenThrow(new IllegalArgumentException("Unable to save employee without id"));

    mockMvc
        .perform(
            put(DEFAULT_URI + "/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(correctEmployee))
        )
        .andDo(print())
        .andExpectAll(
            status().isBadRequest(),
            content().string(containsString("Unable to save employee without id"))
        );
  }

  @Test
  @DisplayName("<= HttpStatus.BAD_REQUEST when add Employee without User =>")
  void updateEmployeeWithoutUserOrWithIncorrectUserId() throws Exception {
    when(employeeServiceImpl.update(any(Employee.class))).thenThrow(new DataNotFoundException("User with id: 1 doesn't exist!"));

    mockMvc
        .perform(
            put(DEFAULT_URI + "/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(correctEmployee))
        )
        .andDo(print())
        .andExpectAll(
            status().isBadRequest(),
            content().string(matchesRegex("User with id: \\d+ doesn't exist!"))
        );
  }

  @Test
  @DisplayName("<= HttpStatus.BAD_REQUEST and List<error.message> when update not valid Employee =>")
  void updateNotValidEmployee() throws Exception{
    mockMvc
        .perform(
            put(DEFAULT_URI + "/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new Employee()))
        )
        .andDo(print())
        .andExpectAll(
            status().isBadRequest(),
            content().string(containsString("\"name\":\"Enter name\"")),
            content().string(containsString("\"surname\":\"Enter surname\"")),
            content().string(containsString("\"phone\":\"Enter phone\"")),
            content().string(containsString("\"salary\":\"Enter salary\"")),
            content().string(containsString("\"enrollmentDate\":\"Enter enrollment date\"")),
            content().string(containsString("\"contractInMonth\":\"Enter the duration of the contract in months\"")),
            content().string(containsString("\"user\":\"Enter userId and Role\""))
        );
  }

  @Test
  @DisplayName("<= HttpStatus.OK and correct data when use getAllRoles =>")
  void getAllRoles() throws Exception {
    Role role1 = new Role(1L, Authority.USER);
    Role role2 = new Role(2L, Authority.ADMIN);
    List<Role> roles = new ArrayList<>(Arrays.asList(role1, role2));

    when(employeeServiceImpl.roles()).thenReturn(roles);

    mockMvc
        .perform(
            get(DEFAULT_URI + "/all-roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(correctEmployee))
        )
        .andDo(print())
        .andExpectAll(
            status().isOk(),
            content().string(mapper.writeValueAsString(roles))
        );
  }

  @Test
  @DisplayName("<= HttpStatus.OK when delete Employee =>")
  void deleteEmployee() throws Exception{
    when(employeeServiceImpl.delete(any(Long.class))).thenReturn(true);

    mockMvc
        .perform(delete(DEFAULT_URI + "/delete?id=1"))
        .andDo(print())
        .andExpect(
            status().isOk()
        );
  }

  @Test
  @DisplayName("<= HttpStatus.BAD_REQUEST when delete Employee which does not exist=>")
  void deleteEmployeeWhichDoesNotExist() throws Exception {
    when(employeeServiceImpl.delete(any(Long.class))).thenThrow( new DataNotFoundException("User with id 1 doesn't exist!"));
    mockMvc
        .perform(delete(DEFAULT_URI + "/delete?id=1"))
        .andDo(print())
        .andExpectAll(
            status().isBadRequest(),
            content().string(matchesRegex("User with id \\d+ doesn't exist!"))
        );
  }
}