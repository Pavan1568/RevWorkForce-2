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

    private int totalDays;
    private int usedDays;

    @ManyToOne(optional = false)
    private LeaveType leaveType;

    @Column(nullable = false)
    private Integer totalAllocated;

    @Column(nullable = false)
    private Integer usedLeaves = 0;

    @Column(nullable = false)
    private Integer remainingLeaves;

    // getters & setters

    public Integer getRemainingLeaves() {
        return remainingLeaves;
    }

    public void setRemainingLeaves(Integer remainingLeaves) {
        this.remainingLeaves = remainingLeaves;
    }

    public Integer getUsedLeaves() {
        return usedLeaves;
    }

    public void setUsedLeaves(Integer usedLeaves) {
        this.usedLeaves = usedLeaves;
    }

    public Integer getTotalAllocated() {
        return totalAllocated;
    }

    public void setTotalAllocated(Integer totalAllocated) {
        this.totalAllocated = totalAllocated;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public int getUsedDays() {
        return usedDays;
    }

    public void setUsedDays(int usedDays) {
        this.usedDays = usedDays;
    }
}

