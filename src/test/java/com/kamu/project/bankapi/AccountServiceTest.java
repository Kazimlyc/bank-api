package com.kamu.project.bankapi;

import com.kamu.project.bankapi.dto.TransferRequestDto;
import com.kamu.project.bankapi.entity.Account;
import com.kamu.project.bankapi.repository.AccountRepository;
import com.kamu.project.bankapi.service.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    private Account senderAccount;
    private Account receiverAccount;

    @BeforeEach
    void setUp() {
        senderAccount = new Account(1L, "TR12345", new BigDecimal("1000.00"), "TL", null);
        receiverAccount = new Account(2L, "TR67890", new BigDecimal("500.00"), "TL", null);
    }

    @Test
    void testTransferMoney_Success() {
        TransferRequestDto request = new TransferRequestDto();
        request.setFromIban("TR12345");
        request.setToIban("TR67890");
        request.setAmount(new BigDecimal("200.00"));

        when(accountRepository.findByIban("TR12345")).thenReturn(Optional.of(senderAccount));
        when(accountRepository.findByIban("TR67890")).thenReturn(Optional.of(receiverAccount));

        accountService.transferMoney(request);

        assertEquals(new BigDecimal("800.00"), senderAccount.getBalance());
        assertEquals(new BigDecimal("700.00"), receiverAccount.getBalance());

        verify(accountRepository, times(1)).save(senderAccount);
        verify(accountRepository, times(1)).save(receiverAccount);
    }

    @Test
    void testTransferMoney_InsufficientBalance_ThrowsException() {
        TransferRequestDto request = new TransferRequestDto();
        request.setFromIban("TR12345");
        request.setToIban("TR67890");
        request.setAmount(new BigDecimal("1200.00"));

        when(accountRepository.findByIban("TR12345")).thenReturn(Optional.of(senderAccount));
        when(accountRepository.findByIban("TR67890")).thenReturn(Optional.of(receiverAccount));

        assertThrows(RuntimeException.class, () -> {
            accountService.transferMoney(request);
        });

        verify(accountRepository, never()).save(any(Account.class));

    }

}
