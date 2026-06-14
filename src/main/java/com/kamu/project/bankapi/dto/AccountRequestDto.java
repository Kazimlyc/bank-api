package com.kamu.project.bankapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class AccountRequestDto {

    @NotNull(message = "Müşteri ID boş olamaz")
    private Long customerId;

    @NotNull(message = "Başlangıç bakiyesi boş olamaz")
    @Min(value = 0, message = "Başlangıç bakiyesi 0'dan küçük olamaz")
    private BigDecimal balance;

    @NotBlank(message = "Döviz türü boş olamaz")
    @Size(min = 3, max = 3, message = "Döviz türü 3 karakter olmalıdır (TRY, USD, EUR)")
    private String currency;


}
