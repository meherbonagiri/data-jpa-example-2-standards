package com.sts.jpa.repository;

import com.sts.jpa.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

    //@Query(value = "SELECT * FROM payroll_employee e WHERE e.emp_Id = :empId", nativeQuery = true)
    @Query(value = "FROM Employee e WHERE e.empId = :empId")
    public Employee getEmployeeById(@Param("empId") int empId);

}
