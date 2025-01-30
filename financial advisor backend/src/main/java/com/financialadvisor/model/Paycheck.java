package com.financialadvisor.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "paychecks")
public class Paycheck {

    @Id
    private String id;
    private String ownerId;  // ✅ Stores the unique user ID instead of username
    private double grossSalary;
    private double netSalary;
    private List<Deduction> deductions;  // ✅ Add deductions

    public Paycheck() {}

    public Paycheck(String ownerId, double grossSalary, double netSalary, List<Deduction> deductions) {
        this.ownerId = ownerId;
        this.grossSalary = grossSalary;
        this.netSalary = netSalary;
        this.deductions = deductions;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }

    public double getGrossSalary() { return grossSalary; }
    public void setGrossSalary(double grossSalary) { this.grossSalary = grossSalary; }

    public double getNetSalary() { return netSalary; }
    public void setNetSalary(double netSalary) { this.netSalary = netSalary; }

    public List<Deduction> getDeductions() { return deductions; }  // ✅ Get deductions
    public void setDeductions(List<Deduction> deductions) { this.deductions = deductions; }  // ✅ Set deductions
}
