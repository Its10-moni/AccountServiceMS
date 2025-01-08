package com.tekarch.AccountServiceMS.Controller;

import com.tekarch.AccountServiceMS.DTO.CombineDTO;
import com.tekarch.AccountServiceMS.DTO.UserDTO;
import com.tekarch.AccountServiceMS.Models.Account;
import com.tekarch.AccountServiceMS.Services.AccountServicesImplm;
import com.tekarch.AccountServiceMS.Services.Interfaces.AccountServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.service.annotation.PutExchange;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping ("/account")
public class AccountController {
    @Autowired
    private AccountServicesImplm accountServices;

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        return new ResponseEntity<>(accountServices.getAllAccounts(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account createdAccount = accountServices.addAccount(account);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Account> updateAccount(@RequestBody Account account) {
        Account createdAccount = accountServices.updateAccount(account);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        Account account = accountServices.getAccountById(id);
        if (account != null) {
            return new ResponseEntity<>(account, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        if (!accountServices.getAccountById(id).getAccountId().equals(0L)) {
            accountServices.deleteAccount(id);
            return ResponseEntity.ok("Account deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found.");
    }

    @GetMapping("/account{accountType}")
    public ResponseEntity<List<Account>> getAccountsByAccountTypes(@RequestParam List<String> accountType) {
        List<Account> accounts = accountServices.getAccountsByAccountTypes(accountType);
        if (accounts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(accounts);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccountById(@PathVariable Long id,@RequestBody Account accountDetails) {
        return accountServices.updateAccountById(id,accountDetails)
                .map(account -> new ResponseEntity<>(account, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


   @GetMapping("/{userid}")
    public CombineDTO getAccountByUserId(@PathVariable Long userid) {
       return accountServices.getAccountByuserId(userid);
   }
    @GetMapping("/{accountId}")
    public Optional<BigDecimal> getAccountBalance(@PathVariable Long accountId) {
        return accountServices.getAccountBalance(accountId);
    }
    }






 /*   @GetMapping
    public ResponseEntity<List<Account>> getLinkedAccountswithUser(@PathVariable Long id) {
        List<UserDTO> user = accountServices.getLinkedAccountswithUserId(id);
        return ResponseEntity.ok(user);
    }*/





