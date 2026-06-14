package com.kamu.project.bankapi.service;


import com.kamu.project.bankapi.dto.CustomerRequestDto;
import com.kamu.project.bankapi.dto.CustomerResponseDto;

import java.util.List;

public interface CustomerService {
    CustomerResponseDto createCustomer(CustomerRequestDto requestDto);
    CustomerResponseDto getCustomerById(Long id);
    List<CustomerResponseDto> getAllCustomers();
}
