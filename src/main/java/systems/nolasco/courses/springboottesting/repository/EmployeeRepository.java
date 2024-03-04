package systems.nolasco.courses.springboottesting.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import systems.nolasco.courses.springboottesting.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
  Optional<Employee> findByEmail(String email);

  @Query("select e from Employee e where e.firstName = ?1 and e.lastName = ?2")
  Employee findByJPQL(String firstName, String lastName);

  @Query("select e from Employee e where e.firstName = :firstName and e.lastName = :lastName")
  Employee findByJPQLNamedParams(@Param("firstName") String firstName, @Param("lastName") String lastName);

  @Query(value = "select * from employees where first_name = ?1 and last_name = ?2", nativeQuery = true)
  Employee findByNativeQuery(String firstName, String lastName);

  @Query(value = "select * from employees where first_name = :firstName and last_name = :lastName", nativeQuery = true)
  Employee findByNativeQueryNamed(@Param("firstName") String firstName, @Param("lastName") String lastName);

}
