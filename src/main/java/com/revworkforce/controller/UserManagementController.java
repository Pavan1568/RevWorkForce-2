package com.revworkforce.controller;

import com.revworkforce.entity.User;
import com.revworkforce.repository.UserRepository;
import com.revworkforce.service.UserManagementService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserManagementController {

    private final UserManagementService userManagementService;
    private final UserRepository userRepository;

    public UserManagementController(UserManagementService userManagementService,
                                    UserRepository userRepository) {
        this.userManagementService = userManagementService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userManagementService.getAllUsers();
    }

    @GetMapping("/count")
    public long getUserCount() {
        return userRepository.count();
    }
}