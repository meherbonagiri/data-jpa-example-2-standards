package com.sts.jpa.services;

import com.sts.jpa.entity.Employee;
import com.sts.jpa.model.EmployeeRequest;
import com.sts.jpa.model.EmployeeResponse;
import com.sts.jpa.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    public EmployeeResponse save(EmployeeRequest employeeRequest) {

        try {
            //generate EMPID
            List<Employee> employeesList = employeeRepo.findAll();
            // 1012
            AtomicInteger maxEmpId = new AtomicInteger();
            employeesList
                    .parallelStream()
                    .max((e1, e2) -> Integer.compare(e1.getEmpId(), e2.getEmpId()))
                    .ifPresent(e -> maxEmpId.set(e.getEmpId()));
            int maxEmpID = maxEmpId.get();
            int NextEmpID = 1001;
            if (maxEmpID != 0) {
                NextEmpID = maxEmpID + 2;
            }

            // generate emailId
            String empEmailId = employeeRequest.getName().concat("@sts.com");

            Employee employee = new Employee();
            employee.setEmpId(NextEmpID);
            employee.setEmailId(empEmailId);
            employee.setName(employeeRequest.getName());
            employee.setExp(employeeRequest.getExp());
            employee.setSkill(employeeRequest.getSkill());
            employee.setCreatedDate(LocalDateTime.now());
            employee.setUpdatedDate(LocalDateTime.now());
            employee.setCreatedUser("STS");
            employee.setUpdatedUser("STS");
            employee.setVersion(1.0);

            Employee savedEmployee = employeeRepo.save(employee);

            // populate employee Response
            EmployeeResponse employeeResponse = new EmployeeResponse();
            employeeResponse.setEmpId(savedEmployee.getEmpId());
            employeeResponse.setEmailId(savedEmployee.getEmailId());
            employeeResponse.setSkill(savedEmployee.getSkill());
            employeeResponse.setExp(savedEmployee.getExp());
            employeeResponse.setName(savedEmployee.getName());

            return employeeResponse;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public EmployeeResponse getEmp(int empId) {
        EmployeeResponse employeeResponse = null;
        try {
            Employee employee = employeeRepo.getEmployeeById(empId);

            employeeResponse = new EmployeeResponse();
            employeeResponse.setEmpId(employee.getEmpId());
            employeeResponse.setEmailId(employee.getEmailId());
            employeeResponse.setSkill(employee.getSkill());
            employeeResponse.setExp(employee.getExp());
            employeeResponse.setName(employee.getName());
            return employeeResponse;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return employeeResponse;
    }

    public List<EmployeeResponse> getAll() {
        List<EmployeeResponse> employeeResponseList = new ArrayList<>();
        try {
            List<Employee> employeeList = employeeRepo.findAll();

            for (Employee emp : employeeList) {
                EmployeeResponse employeeResponse = new EmployeeResponse();
                employeeResponse.setEmpId(emp.getEmpId());
                employeeResponse.setEmailId(emp.getEmailId());
                employeeResponse.setSkill(emp.getSkill());
                employeeResponse.setExp(emp.getExp());
                employeeResponse.setName(emp.getName());

                employeeResponseList.add(employeeResponse);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return employeeResponseList;
    }
}
