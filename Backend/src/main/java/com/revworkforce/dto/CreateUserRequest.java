package com.revworkforce.dto;

import com.revworkforce.entity.User;

public class CreateUserRequest {

    private User user;
    private String roleName;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}