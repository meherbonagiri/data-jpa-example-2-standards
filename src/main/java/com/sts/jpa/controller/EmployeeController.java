package com.sts.jpa.controller;

import com.sts.jpa.model.EmployeeRequest;
import com.sts.jpa.model.EmployeeResponse;
import com.sts.jpa.model.EmployeeUpdateRequest;
import com.sts.jpa.services.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public EmployeeResponse save(@RequestBody EmployeeRequest employeeRequest) {
       // System.out.println("request landed to application with EmployeeRequest= " + employeeRequest);
        log.info("Request landed to application with EmployeeRequest= {}", employeeRequest);
        return employeeService.save(employeeRequest);
    }

    @GetMapping("{empId}")
    public EmployeeResponse get(@PathVariable("empId") int empId) {
        return employeeService.getEmp(empId);
    }

    @GetMapping
    public List<EmployeeResponse> getAll() {
        return employeeService.getAll();
    }

    @DeleteMapping("{empId}")
    public EmployeeResponse delete(@PathVariable("empId") int empId) {
        return employeeService.delete(empId);
    }

    @PutMapping
    public EmployeeResponse update(@RequestBody EmployeeUpdateRequest employeeUpdateRequest) {
        return employeeService.update(employeeUpdateRequest);
    }
}
