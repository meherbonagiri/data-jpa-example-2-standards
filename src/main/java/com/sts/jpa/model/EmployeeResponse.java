package com.sts.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {

    private int empId;
    private String emailId;
    private String name;
    private double exp;
    private String skill;
}
