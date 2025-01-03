package com.tekarch.AccountServiceMS.Controller;

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

import java.util.List;
import java.util.Optional;

@RestController
public class AccountController {
    @Autowired
    private final AccountServicesImplm accountServices;

    public AccountController(AccountServicesImplm accountServices) {
        this.accountServices = accountServices;
    }


    @Autowired
    private RestTemplate restTemplate;
    @Value("${user.service.url}")
    private static String User_URL;


    @PostMapping("/account")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        try {
            Account createdAccount = accountServices.createAccount(account);
            return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
        } catch (Exception e) {
            // If an error occurs while creating the account (e.g., user not found)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping("/account/{userid}")

    public ResponseEntity<Account> getAccountById(@PathVariable("userid") Long userid) {
        Optional<Account> account = accountServices.getAccountById(userid);

        if (account.isPresent()) {
            return new ResponseEntity<>(account.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Account not found
        }
    }

    @GetMapping
    public Iterable<Account> getAllAccounts() {
        return accountServices.getAllAccounts();
    }
    @PutMapping("/account/{userid}")
    public ResponseEntity<Account> updateAccountById(@PathVariable Long userid,@RequestBody Account accountDetails) {
        return accountServices.updateAccountById(userid,accountDetails)
                .map(account -> new ResponseEntity<>(account, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/account{accountTypes}")
    public ResponseEntity<List<Account>> getAccountsByAccountTypes(@RequestParam List<String> accountType) {
        List<Account> accounts = accountServices.getAccountsByAccountTypes(accountType);
        if (accounts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(accounts);
    }
    @GetMapping("/{userId}/account")
    public ResponseEntity<List<UserDTO>> getLinkedAccountswithUserId(@PathVariable Long userId) {
        List<UserDTO> user = accountServices.getLinkedAccountswithUserId(userId);
        return ResponseEntity.ok(user);
    }


    @DeleteMapping("/account/{userid}")
    public ResponseEntity<Account> deleteAccount(@PathVariable Long userid) {
        accountServices.deleteAccount(userid);
        return ResponseEntity.noContent().build();

    }


}