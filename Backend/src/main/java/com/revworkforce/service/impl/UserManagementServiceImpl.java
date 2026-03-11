package com.revworkforce.service.impl;

import com.revworkforce.entity.Role;
import com.revworkforce.entity.User;
import com.revworkforce.repository.RoleRepository;
import com.revworkforce.repository.UserRepository;
import com.revworkforce.service.UserManagementService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.revworkforce.enums.RoleType;

import java.util.Collections;
import java.util.List;

@Service
public class UserManagementServiceImpl implements UserManagementService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserManagementServiceImpl(UserRepository userRepository,
                                     RoleRepository roleRepository,
                                     PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User user, String roleName) {

        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);

        // Assign role
        Role role = roleRepository.findByName(RoleType.valueOf(roleName))
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.setRoles(Collections.singleton(role));

        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUserStatus(Long userId, boolean active) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setActive(active);

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}