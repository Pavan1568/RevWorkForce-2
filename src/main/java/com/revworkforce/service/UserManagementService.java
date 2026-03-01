package com.revworkforce.service;

import com.revworkforce.entity.User;
import java.util.List;

public interface UserManagementService {

    User createUser(User user, String roleName);

    List<User> getAllUsers();

    User updateUserStatus(Long userId, boolean active);

    void deleteUser(Long userId);
}