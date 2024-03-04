package systems.nolasco.courses.springboottesting.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import systems.nolasco.courses.springboottesting.model.Employee;
import systems.nolasco.courses.springboottesting.repository.EmployeeRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

  @Mock
  private EmployeeRepository employeeRepository;
  @InjectMocks
  private EmployeeServiceImpl employeeService;
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
  public void givenEmployeObject_whenSave_thenReturnEmployeeObject() {
    // given
    given(employeeRepository.findByEmail(employee0.getEmail()))
        .willReturn(Optional.empty());
    given(employeeRepository.save(employee0))
        .willReturn(employee0);
    // when
    Employee savedEmployee = employeeService.save(employee0);

    // then
    assertThat(savedEmployee).isNotNull();
    assertThat(savedEmployee.getId()).isEqualTo(1L);

  }

  @Test
  public void givenEmployeObject_whenSave_thenThrowsException() {
    // given
    given(employeeRepository.findByEmail(employee0.getEmail()))
        .willReturn(Optional.of(employee0));

    // when
    assertThrows(ResourceNotFoundException.class, () -> {
      employeeService.save(employee0);
    });

    // then
    verify(employeeRepository, never()).save(any(Employee.class));
  }

  @Test
  public void givenEmployeeList_whenFindAll_thenReturnEmployeeList() {
    // given
    given(employeeRepository.findAll())
        .willReturn(List.of(employee0, employee1));
    // when
    List<Employee> employees = employeeService.getAllEmployees();
    // then
    assertThat(employees).isNotNull();
    assertThat(employees).hasSize(2);
  }

  @Test
  public void givenEmptyEmployeesList_whenFindAll_thenReturnEmptyEmployeesList() {
    // given
    given(employeeRepository.findAll())
        .willReturn(Collections.emptyList());
    // when
    List<Employee> employees = employeeService.getAllEmployees();
    // then
    assertThat(employees).isEmpty();
    assertThat(employees).hasSize(0);
  }

  @Test
  public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {
    // given
    given(employeeRepository.findById(1L))
        .willReturn(Optional.of(employee0));
    // when
    Optional<Employee> employee = employeeService.getEmployeeById(1L);
    // then
    assertThat(employee).isPresent();
  }

  @Test
  public void givenEmployeeId_whenUpdateEmployee_thenReturnEmployeeObject() {
    // given
    given(employeeRepository.findById(1L))
        .willReturn(Optional.of(employee0));
    given(employeeRepository.save(employee0))
        .willReturn(employee0);
    employee0.setEmail("test@mail.com");
    employee0.setFirstName("testfirstname");
    employee0.setLastName("testlastname");
    // when
    Employee updatedEmployee = employeeService.updateEmployee(1L, employee0);
    // then
    assertThat(updatedEmployee).isNotNull();
    assertThat(updatedEmployee.getEmail()).isEqualTo(employee0.getEmail());
    assertThat(updatedEmployee.getFirstName()).isEqualTo(employee0.getFirstName());
    assertThat(updatedEmployee.getLastName()).isEqualTo(employee0.getLastName());
  }

  @Test
  public void givenEmployeeId_whenDeleteEmployee_thenNothing() {
    // given
    long employeeId = 1L;
    willDoNothing().given(employeeRepository).deleteById(employeeId);

    // when
    employeeService.deleteEmployee(employeeId);

    // then
    verify(employeeRepository, times(1))
        .deleteById(employeeId);
  }

  @Test
  public void given_when_then() {
    // given

    // when

    // then

  }

}
