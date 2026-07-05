package com.sts.jpa.repository;

import com.sts.jpa.entity.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

    //@Query(value = "SELECT * FROM payroll_employee e WHERE e.emp_Id = :empId", nativeQuery = true)
    @Query(value = "FROM Employee e WHERE e.empId = :empId")
    Employee getEmployeeById(@Param("empId") int empId);

    //Hibernate: select * from payroll_employee  where emp_id=?
    Employee findByEmpId(int empId);

    @Modifying
    @Query(value = "DELETE FROM Employee e WHERE e.empId = :empId")
    void deleteByEmpId(@Param("empId") int empId);
}
