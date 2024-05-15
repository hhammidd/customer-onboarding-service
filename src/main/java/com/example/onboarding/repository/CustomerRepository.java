package com.example.onboarding.repository;

import com.example.onboarding.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Override
    Optional<Customer> findById(Long aLong);

    Optional<Customer> findByUsername(String username);
}
