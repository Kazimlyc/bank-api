package com.kamu.project.bankapi.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class AccountResponseDto {
    private Long id;
    private String iban;
    private BigDecimal balance;
    private String currency;
    private Long customerId;
}
