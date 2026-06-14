package com.kamu.project.bankapi.service;

import com.kamu.project.bankapi.dto.CustomerRequestDto;
import com.kamu.project.bankapi.dto.CustomerResponseDto;
import com.kamu.project.bankapi.entity.Customer;
import com.kamu.project.bankapi.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public CustomerResponseDto createCustomer(CustomerRequestDto requestDto) {
        customerRepository.findByIdentityNumber(requestDto.getIdentityNumber())
                .ifPresent(c -> {
                    throw new RuntimeException("Bu TC Kimlik numarası zaten sistemde kayıtlı!");
                });

        customerRepository.findByEmail(requestDto.getEmail())
                .ifPresent(c-> {
                    throw new RuntimeException("Bu e-posta adresi zaten sistemde kayıtlı!");
                });

        Customer customer = new Customer();
        customer.setFirstName(requestDto.getFirstName());
        customer.setLastName(requestDto.getLastName());
        customer.setIdentityNumber(requestDto.getIdentityNumber());
        customer.setEmail(requestDto.getEmail());

        Customer savedCustomer = customerRepository.save(customer);

        return mapToResponseDto(savedCustomer);
    }

    @Override
    public CustomerResponseDto getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Müşteri bulunamadı! ID: " + id));
        return mapToResponseDto(customer);
    }

    @Override
    public List<CustomerResponseDto> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    private CustomerResponseDto mapToResponseDto(Customer customer) {
        CustomerResponseDto responseDto = new CustomerResponseDto();
        responseDto.setId(customer.getId());
        responseDto.setFirstName(customer.getFirstName());
        responseDto.setLastName(customer.getLastName());
        responseDto.setIdentityNumber(customer.getIdentityNumber());
        responseDto.setEmail(customer.getEmail());
        return responseDto;
    }


}
