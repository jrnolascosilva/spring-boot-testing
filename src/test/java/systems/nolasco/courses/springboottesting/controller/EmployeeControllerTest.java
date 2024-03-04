package systems.nolasco.courses.springboottesting.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;

import systems.nolasco.courses.springboottesting.model.Employee;
import systems.nolasco.courses.springboottesting.service.EmployeeService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
// Import the necessary class

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private EmployeeService employeeService;

  @Autowired
  private ObjectMapper objectMapper;

  private Employee employee0;
  private Employee employee1;

  @BeforeEach
  public void setup() {
    employee0 = Employee.builder()
        .id(1L)
        .firstName("John")
        .lastName("Doe")
        .email("johndoe@mail.com")
        .build();

    employee1 = Employee.builder()
        .id(2L)
        .firstName("Jane")
        .lastName("Doe")
        .email("janedoe@mail.com")
        .build();
  }

  @Test
  public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee()
      throws JsonProcessingException, Exception {
    // given
    given(employeeService.save(any(Employee.class)))
        .willAnswer(invocation -> invocation.getArgument(0));

  }

  @Test
  public void givenListOfEmployee_whenGetEmployees_then() throws Exception {
    // given
    List<Employee> listOf = List.of(employee0, employee1);
    given(employeeService.getAllEmployees())
        .willReturn(listOf);

    // when
    ResultActions response = mockMvc.perform(get("/api/employees"));

    // then
    response.andExpect(status().isOk())
        .andDo(print()) // Add the missing method call
        .andExpect(jsonPath("$.size()", is(listOf.size())));

  }

  @Test
  public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {
    // given
    long employeeId = 1L;
    given(employeeService.getEmployeeById(employeeId))
        .willReturn(Optional.of(employee0));

    // when
    ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

    // then
    response.andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.firstName", is(employee0.getFirstName())))
        .andExpect(jsonPath("$.lastName", is(employee0.getLastName())))
        .andExpect(jsonPath("$.email", is(employee0.getEmail())));
  }

  @Test
  public void given_when_then() {
    // given

    // when

    // then

  }

}
