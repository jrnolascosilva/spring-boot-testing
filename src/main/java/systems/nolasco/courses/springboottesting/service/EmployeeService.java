package systems.nolasco.courses.springboottesting.service;

import java.util.List;
import java.util.Optional;

import systems.nolasco.courses.springboottesting.model.Employee;

public interface EmployeeService {

  Employee save(Employee employee);

  List<Employee> getAllEmployees();

  Optional<Employee> getEmployeeById(Long id);

  Employee updateEmployee(Long id, Employee employee);

  void deleteEmployee(Long id);

}
