package com.kamu.project.bankapi.service;

import com.kamu.project.bankapi.dto.AccountRequestDto;
import com.kamu.project.bankapi.dto.AccountResponseDto;
import com.kamu.project.bankapi.dto.TransferRequestDto;

import java.util.List;

public interface AccountService {
    AccountResponseDto createAccount(AccountRequestDto requestDto);
    AccountResponseDto getAccountById(Long id);
    List<AccountResponseDto> getAccountsByCustomerId(Long customerId);
    void transferMoney(TransferRequestDto transferDto);

}
