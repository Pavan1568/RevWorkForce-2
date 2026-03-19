package com.revworkforce.controller;

import com.revworkforce.dto.CreateUserRequest;
import com.revworkforce.entity.User;
import com.revworkforce.service.UserManagementService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserManagementController {

    private final UserManagementService userManagementService;

    public UserManagementController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    // CREATE USER
    @PostMapping
    public User createUser(@RequestBody CreateUserRequest request) {

        User user = request.getUser();
        String roleName = request.getRoleName();

        return userManagementService.createUser(user, roleName);
    }

    // GET ALL USERS
    @GetMapping
    public List<User> getAllUsers() {
        return userManagementService.getAllUsers();
    }

    // COUNT USERS
    @GetMapping("/count")
    public long getUserCount() {
        return userManagementService.getAllUsers().size();
    }

    //USER STATUS
    @PutMapping("/{id}/status")
    public User updateUserStatus(@PathVariable Long id,
                                 @RequestParam boolean active) {

        return userManagementService.updateUserStatus(id, active);
    }
}