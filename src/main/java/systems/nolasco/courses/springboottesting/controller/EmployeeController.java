package systems.nolasco.courses.springboottesting.controller;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping; // Add this import statement
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.RequiredArgsConstructor;
import systems.nolasco.courses.springboottesting.model.Employee;
import systems.nolasco.courses.springboottesting.service.EmployeeService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

  private final EmployeeService employeeService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Employee createEmployee(@RequestBody Employee employee) {
    return employeeService.save(employee);
  }

  @GetMapping
  public List<Employee> getEmployees() {
    return employeeService.getAllEmployees();
  }

  @GetMapping("{id}")
  public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
    return employeeService.getEmployeeById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

}
