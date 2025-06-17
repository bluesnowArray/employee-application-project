package com.interview.employeeapplication.controller;

import com.interview.employeeapplication.dto.EmployeeRequest;
import com.interview.employeeapplication.dto.EmployeeResponse;
import com.interview.employeeapplication.entity.Employee;
import com.interview.employeeapplication.exception.ResourceNotFoundException;
import com.interview.employeeapplication.service.EmployeeService;
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
    public List<EmployeeResponse> getAllEmployees() {
        return employeeService.getAllEmployees()
                .stream()
                .map(this::toResponse)
                .toList();
    }


    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id)
                .map(e -> ResponseEntity.ok(toResponse(e)))
                .orElseGet(() ->
                        ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(null)
                );
    }


    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(
            @Valid @RequestBody EmployeeRequest req) {

        Employee saved = employeeService.addEmployee(toEntity(req));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toResponse(saved));
    }


    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequest req) {

        Employee updated = employeeService.getEmployeeById(id)
                .map(existing -> {
                    existing.setName(req.getName());
                    existing.setEmail(req.getEmail());
                    existing.setDepartment(req.getDepartment());
                    return employeeService.updateEmployee(existing);
                })
                .orElseThrow(() ->
                        new ResourceNotFoundException("No employee with id: " + id)
                );

        return ResponseEntity.ok(toResponse(updated));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }


    private Employee toEntity(EmployeeRequest r) {
        Employee e = new Employee();
        e.setName(r.getName());
        e.setEmail(r.getEmail());
        e.setDepartment(r.getDepartment());
        return e;
    }

    private EmployeeResponse toResponse(Employee e) {
        return new EmployeeResponse(
                e.getId(),
                e.getName(),
                e.getEmail(),
                e.getDepartment()
        );
    }
}
