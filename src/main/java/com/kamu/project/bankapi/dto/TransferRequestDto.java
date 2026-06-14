package com.kamu.project.bankapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class TransferRequestDto{

    @NotBlank(message = "Gönderici IBAN boş olamaz")
    private String fromIban;

    @NotBlank(message = "Alıcı IBAN boş olamaz")
    private String toIban;

    @NotNull(message = "Transfer tutarı boş olamaz")
    @Min(value=1, message = "Transfer edilecek tutar en az 1 TL/Birim olmalıdır")
    private BigDecimal amount;


}
