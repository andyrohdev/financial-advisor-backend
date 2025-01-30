package com.financialadvisor.service;

import com.financialadvisor.model.Paycheck;
import com.financialadvisor.repository.PaycheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PaycheckService {

    @Autowired
    private PaycheckRepository paycheckRepository;

    public List<Paycheck> getUserPaychecks(String userId) {
        return paycheckRepository.findByOwnerId(userId);
    }

    public Paycheck savePaycheck(Paycheck paycheck) {
        return paycheckRepository.save(paycheck);
    }

    public Optional<Paycheck> getPaycheckById(String id) {
        return paycheckRepository.findById(id);
    }

    public void deletePaycheck(String id) {
        paycheckRepository.deleteById(id);
    }
}
