package com.interview.employeeapplication.service;

import com.interview.employeeapplication.dto.EmployeeRequest;
import com.interview.employeeapplication.dto.EmployeeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {
    //List<EmployeeResponse> getAllEmployees();
    Page<EmployeeResponse> getAllEmployees(Pageable pageable);
    EmployeeResponse getEmployeeById(Long id);
    EmployeeResponse createEmployee(EmployeeRequest req);
    EmployeeResponse updateEmployee(Long id, EmployeeRequest req);
    void deleteEmployee(Long id);
}
