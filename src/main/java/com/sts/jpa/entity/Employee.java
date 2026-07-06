package com.sts.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

import java.time.LocalDateTime;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "payroll_employee")
public class Employee {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "emp_id", nullable = false, unique = true)
    private int empId;

    @Column(name = "email_id", nullable = false, unique = true)
    private String emailId;

    @Column(name = "name", nullable = false, unique = false)
    private String name;

    @Column(name = "exp", nullable = false, unique = false)
    private double exp;
    @Column(name = "skill", nullable = false, unique = false)
    private String skill;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;

    @Column(name = "created_user", nullable = false, unique = false)
    private String createdUser;

    @Column(name = "updated_user", nullable = false, unique = false)
    private String updatedUser;

    @Column(name = "version", nullable = false, unique = false)
    private double version;
}
