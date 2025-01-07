package com.tekarch.AccountServiceMS.Services;

import com.tekarch.AccountServiceMS.DTO.UserDTO;
import com.tekarch.AccountServiceMS.Models.Account;
import com.tekarch.AccountServiceMS.Repositories.AccountRepositories;
import com.tekarch.AccountServiceMS.Services.Interfaces.AccountServices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServicesImplm implements AccountServices {


    @Autowired
    private RestTemplate restTemplate;
    String User_URL="http://localhost:8080/user";


    @Autowired
    private final AccountRepositories accountRepositories;
    private static final Logger logger = LogManager.getLogger(AccountServicesImplm.class);

    public AccountServicesImplm(AccountRepositories accountRepositories) {
        this.accountRepositories = accountRepositories;
    }
    @Override
    public Account createAccount(Account account) {
        return accountRepositories.save(account);
        }
        /* try {
        UserDTO user = restTemplate.getForObject(User_URL + "/" + account.getAccountId(), UserDTO.class);

        if (user == null) {
            throw new RuntimeException("User not found with ID: " + account.getAccountId());
        }

        // Proceed with account creation
        logger.info("Creating account for user: " + account.getAccountId());catch (HttpClientErrorException | HttpServerErrorException e) {
            logger.error("Error while fetching user details: " + e.getMessage());
            throw new RuntimeException("Error while fetching user data", e);
        }*/

   /* @Override
    public Optional<Account> getAccountByuserId(Long userid) {
        try {
            UserDTO userDTO = restTemplate.getForObject(User_URL + "/" + userid, UserDTO.class);

            if (userDTO == null) {
                logger.warn("User with ID: " + userid + " not found");
            }

            return accountRepositories.findById(userid);
        } catch (Exception e) {
            logger.error("Error fetching account or user data for ID: " + userid, e);
            throw new RuntimeException("Error fetching account", e);
        }
    }*/

    @Override
    public Optional<Account> getAccountByaccounId(Long accountId) {
        return accountRepositories.findById(accountId);
    }

    @Override
    public Account updateAccount(Account account) {
        return accountRepositories.save(account);
    }
    @Override
    public Iterable<Account> getAllAccounts() {
        return accountRepositories.findAll();
    }

  /*  @Override
    public Optional<Account> updateAccountById(Long id,Account accountDetails) {
        Optional<Account> existingAccountOpt =  accountRepositories.findById(id);
        if (existingAccountOpt.isPresent()) {
            Account existingAccount = existingAccountOpt.get();
            existingAccount.setCreatedAt(accountDetails.getCreatedAt());
            existingAccount.setAccountId(accountDetails.getAccountId());
            existingAccount.setBalance(accountDetails.getBalance());
            existingAccount.setCurrency(accountDetails.getCurrency());
            existingAccount.setAccountType(accountDetails.getAccountType());

            return Optional.of(accountRepositories.save(existingAccount));
        }
        return null;
    }*/
    @Override
    public List<Account> getAccountsByAccountTypes(List<String> accountType) {
        return accountRepositories.findByAccountTypeIn(accountType);
    }
    @Override
    public List<UserDTO> getLinkedAccountswithUserId(Long userid) {
            Optional<Account> account = accountRepositories.findById(userid);
                    //.orElseThrow(() -> new UserNotFoundException("User not found"));
            String url = User_URL+"?userid="+userid;
            return List.of(restTemplate.getForObject(url, UserDTO.class));

    }

    public void deleteAccount(Long userid) {
        accountRepositories.deleteById(userid);
    }

}


