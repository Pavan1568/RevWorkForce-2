package com.revworkforce.controller;
import java.util.HashMap;
import java.util.Map;
import com.revworkforce.dto.AuthRequest;
import com.revworkforce.dto.AuthResponse;
import com.revworkforce.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
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

        return new AuthResponse(token);
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
