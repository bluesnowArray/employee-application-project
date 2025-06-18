package com.interview.employeeapplication.service;

import com.interview.employeeapplication.dto.EmployeeRequest;
import com.interview.employeeapplication.dto.EmployeeResponse;
import com.interview.employeeapplication.entity.Employee;
import com.interview.employeeapplication.exception.ResourceConflictException;
import com.interview.employeeapplication.exception.ResourceNotFoundException;
import com.interview.employeeapplication.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    @Override
    public Page<EmployeeResponse> getAllEmployees(Pageable pageable) {
               return repository.findAll(pageable)
                                .map(this::toResponse);
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {
        Employee e = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + id));
        return toResponse(e);
    }

    @Override
    public EmployeeResponse createEmployee(EmployeeRequest req) {
        // status code 409 if email already exists
        if (repository.existsByEmail(req.getEmail())) {
            throw new ResourceConflictException("Email already in use: " + req.getEmail());
        }
        Employee saved = repository.save(toEntity(req));
        return toResponse(saved);
    }

    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest req) {
        Employee existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + id));
        // status code 409 if another record has this email
        repository.findByEmail(req.getEmail())
                .filter(e -> !e.getId().equals(id))
                .ifPresent(e -> {
                    throw new ResourceConflictException("Email already in use: " + req.getEmail());
                });
        existing.setName(req.getName());
        existing.setEmail(req.getEmail());
        existing.setDepartment(req.getDepartment());
        Employee updated = repository.save(existing);
        return toResponse(updated);
    }

    @Override
    public void deleteEmployee(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Employee not found: " + id);
        }
        repository.deleteById(id);
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
