package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AccountService {


    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    // Registers a new user if the username is unique and password is valid.
    public ResponseEntity<Account> registerAccount(Account account) {
        if (account.getUsername().length() == 0 || account.getPassword().length() < 4){
            return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
        } 
        Account existingAccount = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        if (existingAccount != null){
            return new ResponseEntity<> (HttpStatus.CONFLICT);
        }
        Account newAccount = accountRepository.save(account);
        return new ResponseEntity<> (newAccount, HttpStatus.OK);
    }

    // log a user 
    public ResponseEntity<Account> login(Account account){
        Account existingAccount = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        if (existingAccount != null){
            return new ResponseEntity<> (existingAccount, HttpStatus.OK);
        }else{
            return new ResponseEntity<> (HttpStatus.UNAUTHORIZED);
        }
    }


}
