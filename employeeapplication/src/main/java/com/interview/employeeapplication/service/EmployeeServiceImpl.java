package com.interview.employeeapplication.service;
import com.interview.employeeapplication.entity.Employee;
import com.interview.employeeapplication.repository.EmployeeRepository;
import com.interview.employeeapplication.exception.ResourceNotFoundException;
import com.interview.employeeapplication.exception.ResourceConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {

        return employeeRepository.findById(id);
    }


    @Override
    public Employee addEmployee(Employee employee) {
        // Edge case: duplicate email ⇒ 409
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new ResourceConflictException(
                    "Email already in use: " + employee.getEmail()
            );
        }
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        // Edge case: missing record ⇒ 404
        if (!employeeRepository.existsById(employee.getId())) {
            throw new ResourceNotFoundException(
                    "No employee with id: " + employee.getId()
            );
        }
        // Edge case: email used by somebody else ⇒ 409
        employeeRepository.findByEmail(employee.getEmail())
                .filter(e -> !e.getId().equals(employee.getId()))
                .ifPresent(e -> {
                    throw new ResourceConflictException(
                            "Email already in use: " + employee.getEmail()
                    );
                });
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        // Edge case: delete non-existent record ⇒ 404
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "No employee with id: " + id
            );
        }
        employeeRepository.deleteById(id);
    }
}

