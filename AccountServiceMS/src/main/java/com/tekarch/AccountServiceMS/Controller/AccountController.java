package com.tekarch.AccountServiceMS.Controller;

import com.tekarch.AccountServiceMS.Models.Account;
import com.tekarch.AccountServiceMS.Services.AccountServicesImplm;
import com.tekarch.AccountServiceMS.Services.Interfaces.AccountServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.PutExchange;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountServicesImplm accountServices;

    public AccountController(AccountServicesImplm accountServices) {
        this.accountServices = accountServices;
    }

    @Autowired


    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account createdAccount = accountServices.createAccount(account);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @GetMapping("/{userid}")
    public ResponseEntity<Account> getAccount(@PathVariable Long userid) {
        return accountServices.getAccountById(userid)
                .map(account -> new ResponseEntity<>(account, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public Iterable<Account> getAllAccounts() {
        return accountServices.getAllAccounts();
    }
    @PutMapping("/{userid}")
    public ResponseEntity<Account> updateAccountById(@PathVariable Long userid,@RequestBody Account accountDetails) {
        return accountServices.updateAccountById(userid,accountDetails)
                .map(account -> new ResponseEntity<>(account, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("{accountTypes}")
    public ResponseEntity<List<Account>> getAccountsByAccountTypes(@RequestParam List<String> accountType) {
        List<Account> accounts = accountServices.getAccountsByAccountTypes(accountType);
        if (accounts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(accounts);
    }

    @DeleteMapping("/{userid}")
    public ResponseEntity<Account> deleteAccount(@PathVariable Long userid) {
        accountServices.deleteAccount(userid);
        return ResponseEntity.noContent().build();

    }


}