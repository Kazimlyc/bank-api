package com.kamu.project.bankapi.service;

import com.kamu.project.bankapi.dto.AccountRequestDto;
import com.kamu.project.bankapi.dto.AccountResponseDto;
import com.kamu.project.bankapi.dto.CustomerResponseDto;
import com.kamu.project.bankapi.dto.TransferRequestDto;
import com.kamu.project.bankapi.entity.Account;
import com.kamu.project.bankapi.entity.Customer;
import com.kamu.project.bankapi.repository.AccountRepository;
import com.kamu.project.bankapi.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public AccountResponseDto createAccount(AccountRequestDto requestDto){
        Customer cistomer = customerRepository.findById(requestDto.getCustomerId())
                .orElseThrow(() ->  new RuntimeException("Hesap açma başarısız: Müşteri bulunamadı!"));

        String generatedIban = generateUniqueIban();

        Account account = new Account();
        account.setCustomer(cistomer);
        account.setBalance(requestDto.getBalance());
        account.setCurrency(requestDto.getCurrency().toUpperCase());
        account.setIban(generatedIban);

        Account savedAccount = accountRepository.save(account);
        return mapToResponseDto(savedAccount);

    }

    @Override
    public AccountResponseDto getAccountById(Long id){
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hesap bulunamadı! ID: "+id));
        return mapToResponseDto(account);
    }

    @Override
    public List<AccountResponseDto> getAccountsByCustomerId(Long customerId){
        return accountRepository.findByCustomerId(customerId).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void transferMoney(TransferRequestDto transferDto){
        //Ayni ibana transfer kontrolu
        if (transferDto.getFromIban().equals(transferDto.getToIban())) {
            throw new RuntimeException("Aynı hesaba para transferı yapılamaz!");
        }

        //gonderen hesap
        Account fromAccount = accountRepository.findByIban(transferDto.getFromIban())
                .orElseThrow(() -> new RuntimeException("Gönderici hesap bulunamadı! IBAN: "+ transferDto.getFromIban()));

        //alici hesap
        Account toAccount = accountRepository.findByIban(transferDto.getToIban())
                .orElseThrow(()-> new RuntimeException("Alıcı hesap bulunamadı! IBAN: "+transferDto.getToIban()));

        //doviz turu kontrolu
        if (!fromAccount.getCurrency().equals(toAccount.getCurrency())){
            throw new RuntimeException("Farklı para birimleri arasında doğrudan transfer henüz desteklenmemektedir!");
        }

        //bakiye kontrolu
        if(fromAccount.getBalance().compareTo(transferDto.getAmount())<0){
            throw new RuntimeException("Yetersiz bakiye! İşlem engellendi.");
        }

        //hesap bakiyeleri guncelleme
        fromAccount.setBalance(fromAccount.getBalance().subtract(transferDto.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(transferDto.getAmount()));

        //guncel durumlari kayit et
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

    }

    private String generateUniqueIban(){
        Random random = new Random();
        StringBuilder sb= new StringBuilder("TR");
        for(int i = 0; i < 24; i++){
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }





    private AccountResponseDto mapToResponseDto(Account account) {
       AccountResponseDto responseDto = new AccountResponseDto();
        responseDto.setId(account.getId());
        responseDto.setIban(account.getIban());
        responseDto.setBalance(account.getBalance());
        responseDto.setCurrency(account.getCurrency());
        responseDto.setCustomerId(account.getCustomer().getId());

        return responseDto;
    }

}
