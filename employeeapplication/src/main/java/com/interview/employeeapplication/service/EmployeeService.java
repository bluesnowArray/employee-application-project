package com.interview.employeeapplication.service;
import com.interview.employeeapplication.entity.Employee;

import java.util.List;
import java.util.Optional;


public interface EmployeeService {
        List<Employee> getAllEmployees();
      Optional<Employee> getEmployeeById(Long id);
        Employee addEmployee(Employee employee);
        Employee updateEmployee(Employee employee);
        void deleteEmployee(Long id);
    }

