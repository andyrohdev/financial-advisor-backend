package com.financialadvisor.repository;

import com.financialadvisor.model.Paycheck;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface PaycheckRepository extends MongoRepository<Paycheck, String> {
    List<Paycheck> findByOwnerId(String ownerId);  // ✅ Uses ownerId instead of ownerUsername
}
