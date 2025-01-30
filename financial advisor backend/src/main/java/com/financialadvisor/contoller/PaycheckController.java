package com.financialadvisor.controller;

import com.financialadvisor.model.Paycheck;
import com.financialadvisor.service.PaycheckService;
import com.financialadvisor.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/paychecks")
public class PaycheckController {

    @Autowired
    private PaycheckService paycheckService;

    @Autowired
    private JwtUtil jwtUtil;

    /** ✅ Get all paychecks for the authenticated user */
    @GetMapping
    public ResponseEntity<List<Paycheck>> getPaychecks(HttpServletRequest request) {
        String token = extractTokenFromHeader(request);
        String userId = jwtUtil.extractUserIdFromToken(token);

        List<Paycheck> userPaychecks = paycheckService.getUserPaychecks(userId);
        return ResponseEntity.ok(userPaychecks);
    }

    /** ✅ Create a new paycheck */
    @PostMapping
    public ResponseEntity<Paycheck> createPaycheck(@RequestBody Paycheck paycheck, HttpServletRequest request) {
        String token = extractTokenFromHeader(request);
        String userId = jwtUtil.extractUserIdFromToken(token);

        paycheck.setOwnerId(userId);
        Paycheck savedPaycheck = paycheckService.savePaycheck(paycheck);
        return ResponseEntity.ok(savedPaycheck);
    }

    /** ✅ Update an existing paycheck */
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePaycheck(@PathVariable String id, @RequestBody Paycheck paycheck, HttpServletRequest request) {
        String token = extractTokenFromHeader(request);
        String userId = jwtUtil.extractUserIdFromToken(token);

        Optional<Paycheck> existingPaycheck = paycheckService.getPaycheckById(id);

        if (existingPaycheck.isEmpty()) {
            return ResponseEntity.notFound().build(); // 🛑 Paycheck not found
        }

        Paycheck paycheckToUpdate = existingPaycheck.get();

        // ✅ Ensure user can only update their own paychecks
        if (!paycheckToUpdate.getOwnerId().equals(userId)) {
            return ResponseEntity.status(403).body("Unauthorized to update this paycheck");
        }

        // ✅ Update values
        paycheckToUpdate.setGrossSalary(paycheck.getGrossSalary());
        paycheckToUpdate.setNetSalary(paycheck.getNetSalary());
        paycheckToUpdate.setDeductions(paycheck.getDeductions());

        Paycheck updatedPaycheck = paycheckService.savePaycheck(paycheckToUpdate);
        return ResponseEntity.ok(updatedPaycheck);
    }

    /** ✅ Delete a paycheck */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePaycheck(@PathVariable String id, HttpServletRequest request) {
        String token = extractTokenFromHeader(request);
        String userId = jwtUtil.extractUserIdFromToken(token);

        Optional<Paycheck> existingPaycheck = paycheckService.getPaycheckById(id);

        if (existingPaycheck.isEmpty()) {
            return ResponseEntity.notFound().build(); // 🛑 Paycheck not found
        }

        Paycheck paycheckToDelete = existingPaycheck.get();

        // ✅ Ensure user can only delete their own paychecks
        if (!paycheckToDelete.getOwnerId().equals(userId)) {
            return ResponseEntity.status(403).body("Unauthorized to delete this paycheck");
        }

        paycheckService.deletePaycheck(id);
        return ResponseEntity.ok("Paycheck deleted successfully");
    }

    /** ✅ Extracts token from the Authorization header */
    private String extractTokenFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null; // Handle missing token case properly
    }
}
