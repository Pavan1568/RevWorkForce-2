package com.revworkforce.dto;

import java.util.Set;

public class UserResponseDTO {

    private Long id;
    private String email;
    private String employeeId;
    private boolean active;
    private Set<String> roles;

    public UserResponseDTO() {}

    public UserResponseDTO(Long id, String email, String employeeId, boolean active, Set<String> roles) {
        this.id = id;
        this.email = email;
        this.employeeId = employeeId;
        this.active = active;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public boolean isActive() {
        return active;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}