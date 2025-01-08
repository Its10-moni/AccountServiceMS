package com.tekarch.AccountServiceMS.Services;

import com.tekarch.AccountServiceMS.DTO.CombineDTO;
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
import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServicesImplm implements AccountServices {

    @Autowired
    private RestTemplate restTemplate;
    String User_URL = "http://localhost:8080/user";

    @Autowired
    private AccountRepositories accountRepositories;
    private static final Logger logger = LogManager.getLogger(AccountServicesImplm.class);

    @Override
    public List<Account> getAllAccounts() {

        return accountRepositories.findAll();
    }

    @Override
    public Account getAccountById(Long accountId) {

        return accountRepositories.findById(accountId).orElse(null);
    }

    @Override
    public Account addAccount(Account accounts) {

        return accountRepositories.save(accounts);
    }

    @Override
    public void deleteAccount(Long accountId) {

        accountRepositories.deleteById(accountId);
    }

    @Override
    public Account updateAccount(Account accounts) {

        return accountRepositories.save(accounts);
    }

    @Override
    public List<Account> getAccountsByAccountTypes(List<String> accountType) {
        return accountRepositories.findByAccountTypeIn(accountType);
    }

    @Override
    public Optional<Account> updateAccountById(Long id, Account accountDetails) {
        Optional<Account> existingAccountOpt = accountRepositories.findById(id);
        if (existingAccountOpt.isPresent()) {
            Account existingAccount = existingAccountOpt.get();
            existingAccount.setAccountId(accountDetails.getAccountId());
            existingAccount.setBalance(accountDetails.getBalance());
            existingAccount.setCurrency(accountDetails.getCurrency());
            existingAccount.setAccountType(accountDetails.getAccountType());

            return Optional.of(accountRepositories.save(existingAccount));
        }
        return null;
    }

    @Override
    public CombineDTO getAccountByuserId(Long userid) {
        UserDTO user = restTemplate.getForObject(User_URL + "/", UserDTO.class);
        if (user == null) {
            throw new RuntimeException("User not found for userId: " + userid);
        }
        Optional<Account> account = accountRepositories.findByUserId(userid);
        if (account.isEmpty()) {
            throw new RuntimeException("Account not found for userId: " + userid);
        }
        CombineDTO response = new CombineDTO();
        response.setUser(user);
        response.setAccount(account.get());

        return response;
    }

    @Override
    public Optional<BigDecimal> getAccountBalance(Long accountId) {
        Optional<BigDecimal> balance = accountRepositories.findBalanceByAccountId(accountId);
        if (balance.isEmpty()) {
            throw new RuntimeException("Account not found for accountId: " + accountId);
        }
        return balance;
    }
}
       /* try

    {

        UserDTO user = restTemplate.getForObject(User_URL + "/" + account.getAccountId(), UserDTO.class);

        if (user == null) {
            throw new RuntimeException("User not found with ID: " + account.getAccountId());
        }

        // Proceed with account creation
        logger.info("Creating account for user: " + account.getAccountId());catch
        (HttpClientErrorException | HttpServerErrorException e){
        logger.error("Error while fetching user details: " + e.getMessage());
        throw new RuntimeException("Error while fetching user data", e);
    }

    }
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
    }

  /*
  //  @Override
  /*  public List<UserDTO> getLinkedAccountswithUser(Account accounts) {
            Optional<Account> account = accountRepositories.findById(userid);
                    //.orElseThrow(() -> new UserNotFoundException("User not found"));
            String url = User_URL+"?userid="+userid;
            return List.of(restTemplate.getForObject(url, UserDTO.class));

    }*/






