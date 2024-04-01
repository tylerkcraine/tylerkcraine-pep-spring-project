package com.example.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.AccountExistsException;
import com.example.exception.ClientErrorException;
import com.example.exception.UnauthorizedException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    /**
     * Service method to register new account to database
     * @param newAccount account to be added to database
     * @return account added to database with me user id
     * @throws ClientErrorException when username blank or password not long enough (<4)
     * @throws AccountExistsException if account already in database
     */
    public Account addAccount(Account newAccount) {
        if (newAccount.getUsername().isBlank() || newAccount.getPassword().length() < 4) {
            throw new ClientErrorException("Invalid account fields in registration");
        }
        if (accountRepository.getAccountByUsername(newAccount.getUsername()) != null) {
            throw new AccountExistsException("Account already registered");
        }

        return accountRepository.saveAndFlush(newAccount);
    }

    /**
     * Service method to login a user
     * @param loginAccount representing the account details entered
     * @return account retreived from database
     * @throws UnauthorizedException when account doesn't exist or password incorrect
     */
    public Account login(Account loginAccount) {
        Account account = accountRepository.getAccountByUsername(loginAccount.getUsername());
        if (account == null) {
            throw new UnauthorizedException("Username or password incorrect");
        }

        if (loginAccount.getPassword().contentEquals(account.getPassword())) {
            return account;
        } else {
            throw new UnauthorizedException("Username or password incorrect");
        }
    }


}
