package com.revworkforce.controller;
import java.util.HashMap;
import java.util.Map;
import com.revworkforce.dto.AuthRequest;
import com.revworkforce.dto.AuthResponse;
import com.revworkforce.entity.Employee;
import com.revworkforce.entity.User;
import com.revworkforce.repository.EmployeeRepository;
import com.revworkforce.repository.UserRepository;
import com.revworkforce.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;


    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          EmployeeRepository employeeRepository,
                          JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String token = jwtUtil.generateToken(request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Employee employee = employeeRepository.findByUser(user).orElse(null);

        Long employeeId = employee != null ? employee.getId() : null;

        return new AuthResponse(token, employeeId);
    }

//    @PostMapping("/login")
//    public Map<String, String> login(@RequestBody AuthRequest request) {
//
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getEmail(),
//                        request.getPassword()
//                )
//        );
//
//        String token = jwtUtil.generateToken(request.getEmail());
//
//        Map<String, String> response = new HashMap<>();
//        response.put("token", token);
//
//        return response;
    }
