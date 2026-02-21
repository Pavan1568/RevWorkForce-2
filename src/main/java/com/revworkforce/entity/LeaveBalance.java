package com.revworkforce.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "leave_balances")
public class LeaveBalance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Employee employee;

    @ManyToOne(optional = false)
    private LeaveType leaveType;

    @Column(nullable = false)
    private Integer totalAllocated;

    @Column(nullable = false)
    private Integer usedLeaves = 0;

    @Column(nullable = false)
    private Integer remainingLeaves;

    // getters & setters
}