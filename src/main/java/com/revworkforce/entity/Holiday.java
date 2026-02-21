package com.revworkforce.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "holidays")
public class Holiday extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate date;

    private String description;

    // getters & setters
}