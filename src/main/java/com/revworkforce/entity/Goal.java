package com.revworkforce.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "goals")
public class Goal extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    private LocalDate deadline;

    private Integer progress; // 0 - 100

    @Column(length = 2000)
    private String selfFeedback;

    @Column(nullable = false)
    private String status; // ACTIVE / COMPLETED

    // ===== Getters & Setters =====

    public Long getId() { return id; }

    public Employee getEmployee() { return employee; }

    public void setEmployee(Employee employee) { this.employee = employee; }

    public Integer getYear() { return year; }

    public void setYear(Integer year) { this.year = year; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public LocalDate getDeadline() { return deadline; }

    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    public Integer getProgress() { return progress; }

    public void setProgress(Integer progress) { this.progress = progress; }

    public String getSelfFeedback() { return selfFeedback; }

    public void setSelfFeedback(String selfFeedback) { this.selfFeedback = selfFeedback; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}