package com.example.service;

import javax.security.auth.login.AccountException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.AccountExistsException;
import com.example.exception.ClientErrorException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    public Account addAccount(Account newAccount) {
        if (newAccount.getUsername().isBlank() || newAccount.getPassword().length() < 4) {
            throw new ClientErrorException("Invalid account fields in registration");
        }
        if (accountRepository.getAccountByUsername(newAccount.getUsername()) != null) {
            throw new AccountExistsException("Account already registered");
        }

        accountRepository.saveAndFlush(newAccount);
        return accountRepository.getAccountByUsername(newAccount.getUsername());
    }
}
