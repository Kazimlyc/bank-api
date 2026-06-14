package com.kamu.project.bankapi.controller;

import com.kamu.project.bankapi.dto.AccountRequestDto;
import com.kamu.project.bankapi.dto.AccountResponseDto;
import com.kamu.project.bankapi.dto.TransferRequestDto;
import com.kamu.project.bankapi.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    //yeni banka hesabi acma (POST http://localhost:8080/api/v1/accounts)
    @PostMapping
    public ResponseEntity<AccountResponseDto> createAccount(@Valid @RequestBody AccountRequestDto requestDto){
        AccountResponseDto createdAccount = accountService.createAccount(requestDto);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    //bir musteriye ait tum hesaplari listeleme (GET http://localhost:8080/api/v1/accounts/customer/{customerId})
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<AccountResponseDto>> getAccountsByCustomerId(@PathVariable Long customerId){
        List<AccountResponseDto> accounts = accountService.getAccountsByCustomerId(customerId);
        return ResponseEntity.ok(accounts);
    }

    //para transfer endpointi (POST http://localhost:8080/api/v1/accounts/transfer)
    @PostMapping("/transfer")
    public ResponseEntity<String> transferMoney(@Valid @RequestBody TransferRequestDto transferDto){
        accountService.transferMoney(transferDto);
        return ResponseEntity.ok("Para transferi başarıyla gerçekleştirildi.");
    }

}
