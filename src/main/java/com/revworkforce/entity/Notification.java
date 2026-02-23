package com.revworkforce.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "notifications")
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Employee manager;

    @Column(nullable = false, length = 1000)
    private String message;

    @Column(nullable = false)
    private boolean readStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    // Getters & Setters

    public Long getId() { return id; }

    public Employee getManager() { return manager; }

    public void setManager(Employee manager) { this.manager = manager; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public boolean isReadStatus() { return readStatus; }

    public void setReadStatus(boolean readStatus) { this.readStatus = readStatus; }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}