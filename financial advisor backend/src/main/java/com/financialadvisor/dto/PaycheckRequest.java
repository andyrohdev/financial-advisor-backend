package com.financialadvisor.dto;

import com.financialadvisor.model.Deduction;
import lombok.Data;
import java.util.List;

@Data
public class PaycheckRequest {
    private String userId;
    private double grossIncome;
    private List<Deduction> deductions;
}
