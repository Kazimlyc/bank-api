package com.kamu.project.bankapi.dto;

import lombok.Data;

@Data
public class CustomerResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String identityNumber;
    private String email;
}
