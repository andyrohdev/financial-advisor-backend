package com.financialadvisor.model;

public class Deduction {
    private String name;
    private double amount;

    public Deduction() {}

    public Deduction(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}
