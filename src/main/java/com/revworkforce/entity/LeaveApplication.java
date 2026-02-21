package com.revworkforce.entity;

import com.revworkforce.enums.LeaveStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "leave_applications")
public class LeaveApplication extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Employee employee;

    @ManyToOne(optional = false)
    private LeaveType leaveType;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String reason;

    @Enumerated(EnumType.STRING)
    private LeaveStatus status = LeaveStatus.PENDING;

    private String managerComments;

    // getters & setters
}