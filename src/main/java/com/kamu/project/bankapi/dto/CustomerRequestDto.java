package com.kamu.project.bankapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerRequestDto {

    @NotBlank(message = "Müşteri adı boş olamaz")
    @Size(min = 2, max = 100, message = "Müşteri adı 2 ile 100 karakter arasında olmalıdır")
    private String firstName;

    @NotBlank(message = "Müşteri soyadı boş olamaz")
    @Size(min = 2, max = 100, message = "Müşteri soyadı 2 ile 100 karakter arasında olmalı")
    private String lastName;

    @NotBlank(message = "TC Kimlik numarası boş olamaz")
    @Size(min = 11, max = 11,message = "TC Kimlik numarası tam 11 haneli olmalıdır")
    private String identityNumber;

    @NotBlank(message = "E-posta adresi boş olamaz")
    @Email(message = "Lütfen geçerli bir e-posta adresi giriniz")
    private String email;



}
