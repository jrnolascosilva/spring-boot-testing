package systems.nolasco.courses.springboottesting.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import systems.nolasco.courses.springboottesting.model.Employee;
import systems.nolasco.courses.springboottesting.repository.EmployeeRepository;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeRepository employeeRepository;

  @Override
  public Employee save(Employee employee) {

    Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());

    if (savedEmployee.isPresent()) {
      throw new ResourceNotFoundException("Employee with email " + employee.getEmail() + " already exists");
    }

    return employeeRepository.save(employee);
  }

  @Override
  public List<Employee> getAllEmployees() {
    return employeeRepository.findAll();
  }

  @Override
  public Optional<Employee> getEmployeeById(Long id) {
    return employeeRepository.findById(id);
  }

  @Override
  public Employee updateEmployee(Long id, Employee employee) {

    Employee savedEmployee = employeeRepository.findById(id)
        .orElseThrow(
            () -> new ResourceNotFoundException("Employee with id " + id + " not found"));

    savedEmployee.setEmail(employee.getEmail());
    savedEmployee.setFirstName(employee.getFirstName());
    savedEmployee.setLastName(employee.getLastName());
    // employee.setId(id);
    Employee updatedEmployee = employeeRepository.save(savedEmployee);

    return updatedEmployee;
  }

  @Override
  public void deleteEmployee(Long id) {
    employeeRepository.deleteById(id);
  }

}
