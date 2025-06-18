package com.interview.employeeapplication.controller;

import com.interview.employeeapplication.dto.EmployeeRequest;
import com.interview.employeeapplication.dto.EmployeeResponse;
import com.interview.employeeapplication.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService svc;

    @GetMapping
    public Page<EmployeeResponse> getAllEmployees(Pageable pageable) {
                return svc.getAllEmployees(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {
        //service  throws 404 exception if missing
        EmployeeResponse resp = svc.getEmployeeById(id);
        return ResponseEntity.ok(resp);
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(
            @Valid @RequestBody EmployeeRequest req) {
        EmployeeResponse created = svc.createEmployee(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequest req) {
        EmployeeResponse updated = svc.updateEmployee(id, req);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        svc.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }
}
