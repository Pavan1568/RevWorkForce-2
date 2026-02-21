package com.revworkforce.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
public class Employee extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String emergencyContact;

    private LocalDate joiningDate;

    private BigDecimal salary;

    @ManyToOne
    private Department department;

    @ManyToOne
    private Designation designation;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;

    // getters & setters
}