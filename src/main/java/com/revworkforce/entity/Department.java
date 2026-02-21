package com.revworkforce.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "departments")
public class Department extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    // getters & setters
}