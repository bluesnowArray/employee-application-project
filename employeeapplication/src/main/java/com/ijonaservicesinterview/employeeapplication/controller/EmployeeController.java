package com.ijonaservicesinterview.employeeapplication.controller;

import com.ijonaservicesinterview.employeeapplication.entity.Employee;
import com.ijonaservicesinterview.employeeapplication.exception.ResourceNotFoundException;
import com.ijonaservicesinterview.employeeapplication.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable(value = "id") Long employeeId) {

            Employee employee = employeeService.getEmployeeById(employeeId);

        if (employee != null) {
            return ResponseEntity.ok().body(employee);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee Not Found With id:"+employeeId);
        }


    }

    @PostMapping
    public Employee createEmployee(@Valid @RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
                                                   @Valid @RequestBody Employee employeeDetails) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        employee.setName(employeeDetails.getName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setDepartment(employeeDetails.getDepartment());
        final Employee updatedEmployee = employeeService.updateEmployee(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable(value = "id") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok().build();
    }
}