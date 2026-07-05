package com.sts.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*@Data
public class EmployeeRequest {
    private String name;
    private double exp;
    private String skill;
}*/

public record EmployeeRequest(
        String name,
        double exp,
        String skill) {
}
