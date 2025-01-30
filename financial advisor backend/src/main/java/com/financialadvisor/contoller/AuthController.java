package com.financialadvisor.controller;

import com.financialadvisor.model.User;
import com.financialadvisor.repository.UserRepository;
import com.financialadvisor.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")  // ✅ API base path
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already taken!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword())); // ✅ Hash password
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

        // ✅ Retrieve user ID from the database
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(401).body("User not found.");
        }

        String userId = optionalUser.get().getId();  // ✅ Get the user's ID

        // ✅ Generate JWT with both userId and username
        String token = jwtUtil.generateToken(userId, userDetails.getUsername());

        return ResponseEntity.ok(token);
    }

}
