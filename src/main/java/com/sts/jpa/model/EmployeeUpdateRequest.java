package com.sts.jpa.model;

/*@Data
public class EmployeeRequest {
    private String name;
    private double exp;
    private String skill;
}*/

public record EmployeeUpdateRequest(
        int empId,
        String name,
        double exp,
        String skill) {
}
