package systems.nolasco.courses.springboottesting.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import systems.nolasco.courses.springboottesting.model.Employee;

@DataJpaTest
public class EmployeeRepositoryTest {

  @Autowired
  private EmployeeRepository employeeRepository;

  private Employee employee0;

  @BeforeEach
  public void setUp() {
    employee0 = Employee.builder()
        .firstName("John")
        .lastName("Doe")
        .email("johndoe@mail.com")
        .build();
  }

  @Test
  public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {

    // given

    // when
    Employee savedEmployee = employeeRepository.save(employee0);

    // then
    assertThat(savedEmployee).isNotNull();
    assertThat(savedEmployee.getId()).isGreaterThan(0L);
  }

  @Test
  public void givenEmployeeList_whenFindAll_thenReturnEmployeeList() {

    // given

    Employee employee1 = Employee.builder()
        .firstName("Jane")
        .lastName("Doe")
        .email("janedoe@mail.com")
        .build();

    employeeRepository.save(employee0);
    employeeRepository.save(employee1);

    // when
    Iterable<Employee> employees = employeeRepository.findAll();

    // then
    assertThat(employees).isNotEmpty();
    assertThat(employees).hasSize(2);
  }

  @Test
  public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {

    // given
    employeeRepository.save(employee0);

    // when
    Employee employeeDB = employeeRepository.findById(employee0.getId())
        .orElse(null);

    // then
    assertThat(employeeDB).isNotNull();
  }

  @Test
  public void givenEmployeeObject_whenFindByEmail_thenReturnEmployeeObject() {

    // given
    employeeRepository.save(employee0);

    // when
    Employee employeeDB = employeeRepository.findByEmail(employee0.getEmail()).orElse(null);

    // then
    assertThat(employeeDB).isNotNull();
    assertThat(employeeDB.getEmail()).isEqualTo(employee0.getEmail());

  }

  @Test
  public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
    // given
    employeeRepository.save(employee0);

    // when
    Employee savedEmployee = employeeRepository.findById(employee0.getId()).orElse(null);
    savedEmployee.setFirstName("Test");
    savedEmployee.setEmail("test1@mail.com");

    Employee updatedEmployee = employeeRepository.save(savedEmployee);

    // then
    assertThat(updatedEmployee).isNotNull();
    assertThat(updatedEmployee.getId()).isEqualTo(employee0.getId());
    assertThat(updatedEmployee.getFirstName()).isEqualTo("Test");
    assertThat(updatedEmployee.getEmail()).isEqualTo("test1@mail.com");
  }

  @Test
  public void givenEmployeeObject_whenDeleteEmployee_thenRemoveEmployee() {
    // given
    employeeRepository.save(employee0);

    // when
    employeeRepository.deleteById(employee0.getId());
    Optional<Employee> employeeDB = employeeRepository.findById(employee0.getId());

    // then
    assertThat(employeeDB).isEmpty();
  }

  @Test
  public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject() {
    // given

    employeeRepository.save(employee0);
    String firstName = "John";
    String lastName = "Doe";

    // when
    Employee employeeDB = employeeRepository.findByJPQL(firstName, lastName);

    // then

    assertThat(employeeDB).isNotNull();
    assertThat(employeeDB.getFirstName()).isEqualTo(firstName);
    assertThat(employeeDB.getLastName()).isEqualTo(lastName);

  }

  @Test
  public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject() {
    // given
    employeeRepository.save(employee0);
    String firstName = "John";
    String lastName = "Doe";

    // when
    Employee employeeDB = employeeRepository.findByJPQLNamedParams(firstName, lastName);

    // then

    assertThat(employeeDB).isNotNull();
    assertThat(employeeDB.getFirstName()).isEqualTo(firstName);
    assertThat(employeeDB.getLastName()).isEqualTo(lastName);

  }

  @Test
  public void givenFirstNameAndLastName_whenFindByNativeQuery_thenReturnEmployeeObject() {
    // given
    employeeRepository.save(employee0);

    // when
    Employee employeeDB = employeeRepository.findByNativeQuery(employee0.getFirstName(), employee0.getLastName());

    // then

    assertThat(employeeDB).isNotNull();
    assertThat(employeeDB.getFirstName()).isEqualTo(employee0.getFirstName());
    assertThat(employeeDB.getLastName()).isEqualTo(employee0.getLastName());

  }

  @Test
  public void givenFirstNameAndLastName_whenFindByNativeQueryNamed_thenReturnEmployeeObject() {
    // given
    employeeRepository.save(employee0);

    // when
    Employee employeeDB = employeeRepository.findByNativeQueryNamed(employee0.getFirstName(), employee0.getLastName());

    // then

    assertThat(employeeDB).isNotNull();
    assertThat(employeeDB.getFirstName()).isEqualTo(employee0.getFirstName());
    assertThat(employeeDB.getLastName()).isEqualTo(employee0.getLastName());

  }

}