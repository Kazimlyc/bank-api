package com.kamu.project.bankapi.repository;

import com.kamu.project.bankapi.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByIdentityNumber(String identityNumber);

    Optional<Customer> findByEmail(String email);

}
