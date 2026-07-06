package com.sts.jpa.services;

import com.sts.jpa.entity.Employee;
import com.sts.jpa.exception.FailedToFetchEmployeeRecords;
import com.sts.jpa.model.EmployeeRequest;
import com.sts.jpa.model.EmployeeResponse;
import com.sts.jpa.model.EmployeeUpdateRequest;
import com.sts.jpa.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
            int NextEmpID = getNextEmpID();
            String empEmailId = generateEmailId(employeeRequest.name());

            Employee employee = new Employee();
            employee.setEmpId(NextEmpID);
            employee.setEmailId(empEmailId);
            employee.setName(employeeRequest.name());
            employee.setExp(employeeRequest.exp());
            employee.setSkill(employeeRequest.skill());
           // employee.setCreatedDate(LocalDateTime.now());
           // employee.setUpdatedDate(LocalDateTime.now());
            employee.setCreatedUser("STS");
            employee.setUpdatedUser("STS");
            employee.setVersion(1.0);

            Employee savedEmployee = employeeRepo.save(employee);

            // populate employee Response
            EmployeeResponse employeeResponse = getEmployeeResponse(savedEmployee);

            return employeeResponse;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private EmployeeResponse getEmployeeResponse(Employee savedEmployee) {
       /* EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setEmpId(savedEmployee.getEmpId());
        employeeResponse.setEmailId(savedEmployee.getEmailId());
        employeeResponse.setSkill(savedEmployee.getSkill());
        employeeResponse.setExp(savedEmployee.getExp());
        employeeResponse.setName(savedEmployee.getName());
        return employeeResponse;*/

        return EmployeeResponse.builder()
                .empId(savedEmployee.getEmpId())
                .emailId(savedEmployee.getEmailId())
                .name(savedEmployee.getName())
                .exp(savedEmployee.getExp())
                .skill(savedEmployee.getSkill())
                .build();
    }

    private String generateEmailId(String name) {
        String empEmailId = name.concat("@sts.com");
        return empEmailId;
    }

    private int getNextEmpID() throws FailedToFetchEmployeeRecords {
        int NextEmpID = 0;
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
            NextEmpID = 1001;
            if (maxEmpID != 0) {
                NextEmpID = maxEmpID + 2;
            }
        } catch (Exception e) {
            throw new FailedToFetchEmployeeRecords(e);
        }
        return NextEmpID;
    }

    public EmployeeResponse getEmp(int empId) {
        EmployeeResponse employeeResponse = null;
        try {
            //Employee employee = employeeRepo.getEmployeeById(empId);
            Employee employee = employeeRepo.findByEmpId(empId);
            employeeResponse = getEmployeeResponse(employee);
        } catch (Exception e) {
            employeeResponse = EmployeeResponse.builder()
                    .code(404)
                    .description("Employee Not Found with empId=".concat(String.valueOf(empId)))
                    .build();
        }
        return employeeResponse;
    }

    public List<EmployeeResponse> getAll() {
        List<EmployeeResponse> employeeResponseList = new ArrayList<>();
        try {
            List<Employee> employeeList = employeeRepo.findAll();

            for (Employee emp : employeeList) {
                EmployeeResponse employeeResponse = getEmployeeResponse(emp);
                employeeResponseList.add(employeeResponse);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return employeeResponseList;
    }

    public EmployeeResponse delete(int empId) {
        EmployeeResponse employeeResponse = new EmployeeResponse();
        try {
            employeeRepo.deleteByEmpId(empId);
            employeeResponse.setCode(200);
            employeeResponse.setDescription("Employee Deleted Successfully");
        } catch (Exception e) {
            employeeResponse.setCode(500);
            employeeResponse.setDescription("Employee Failed to delete with empId=".concat(String.valueOf(empId)));
        }
        return employeeResponse;
    }

    public EmployeeResponse update(EmployeeUpdateRequest employeeUpdateRequest) {
        try {
            final int empId = employeeUpdateRequest.empId();

            Employee employee = employeeRepo.findByEmpId(empId);

            if (employeeUpdateRequest.exp() != 0) {
                employee.setExp(employeeUpdateRequest.exp());
            }

            if (!StringUtils.isEmpty(employeeUpdateRequest.skill())) { //employeeUpdateRequest.skill() != null && !employeeUpdateRequest.skill().equals("")
                String existingSkill = employee.getSkill();
                String updatedSkill = existingSkill.concat(",").concat(employeeUpdateRequest.skill());
                employee.setSkill(updatedSkill);
            }

            // replaced with @UpdatedTimeStamp in entity
           // employee.setUpdatedDate(LocalDateTime.now());
            Employee savedEmployee = employeeRepo.save(employee);

            EmployeeResponse employeeResponse = getEmployeeResponse(savedEmployee);
            employeeResponse.setCode(2001);
            employeeResponse.setDescription("Employee Updated Successfully");
            return employeeResponse;

        } catch (Exception e) {
            EmployeeResponse employeeResponse = new EmployeeResponse();
            employeeResponse.setCode(5001);
            employeeResponse.setDescription("Failed to update employee");
            return employeeResponse;
        }
    }
}
