package com.revworkforce.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "designations")
public class Designation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    // getters & setters
}