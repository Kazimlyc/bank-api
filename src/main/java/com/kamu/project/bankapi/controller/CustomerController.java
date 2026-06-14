package com.kamu.project.bankapi.controller;

import com.kamu.project.bankapi.dto.CustomerRequestDto;
import com.kamu.project.bankapi.dto.CustomerResponseDto;
import com.kamu.project.bankapi.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    //yeni musteri ekleme (POST http://localhost:8080/api/v1/customers)
    @PostMapping
    public ResponseEntity<CustomerResponseDto> createCustomer(@Valid @RequestBody CustomerRequestDto requestDto){
        CustomerResponseDto createdCustomer = customerService.createCustomer(requestDto);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    //id ile musteri getirme (GET http://localhost:8080/api/v1/customers{id})
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable Long id){
        CustomerResponseDto customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    //tum musterileri listeleme (GET http://localhost:8080/api/v1/customers)
    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomers(){
        List<CustomerResponseDto> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

}
