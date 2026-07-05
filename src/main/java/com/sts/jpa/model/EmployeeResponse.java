package com.sts.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeResponse {

    private int empId;
    private String emailId;
    private String name;
    private double exp;
    private String skill;

    private int errorCode;
    private String errorDescription;
}
