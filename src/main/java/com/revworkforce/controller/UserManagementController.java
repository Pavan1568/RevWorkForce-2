package com.revworkforce.controller;
import com.revworkforce.dto.UserResponseDTO;
import java.util.stream.Collectors;
import com.revworkforce.dto.UserResponseDTO;
import com.revworkforce.entity.User;
import com.revworkforce.service.UserManagementService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.revworkforce.dto.CreateUserRequest;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserManagementController {

    private final UserManagementService userManagementService;

    public UserManagementController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    // ✅ Create User
    @PostMapping
    public User createUser(@RequestBody CreateUserRequest request) {
        return userManagementService.createUser(request.getUser(), request.getRoleName());
    }

    // ✅ Get All Users
    @GetMapping
    public List<UserResponseDTO> getAllUsers() {

        return userManagementService.getAllUsers()
                .stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getEmail(),
                        user.getEmployeeId(),
                        user.isActive(),
                        user.getRoles()
                                .stream()
                                .map(role -> role.getName().name()) // assuming Role has getName()
                                .collect(java.util.stream.Collectors.toSet())
                ))
                .toList();
    }
    // ✅ Activate / Deactivate User
    @PutMapping("/{id}/status")
    public User updateUserStatus(@PathVariable Long id,
                                 @RequestParam boolean active) {
        return userManagementService.updateUserStatus(id, active);
    }

    // ✅ Delete User
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userManagementService.deleteUser(id);
    }
}