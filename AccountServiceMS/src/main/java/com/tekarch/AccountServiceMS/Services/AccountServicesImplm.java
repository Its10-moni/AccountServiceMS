package com.tekarch.AccountServiceMS.Services;

import com.tekarch.AccountServiceMS.DTO.UserDTO;
import com.tekarch.AccountServiceMS.Models.Account;
import com.tekarch.AccountServiceMS.Repositories.AccountRepositories;
import com.tekarch.AccountServiceMS.Services.Interfaces.AccountServices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServicesImplm implements AccountServices {
     @Autowired
    private RestTemplate restTemplate;
    private static final String User_URL="http://localhost:8080/user";

    @Autowired
    private final AccountRepositories accountRepositories;
    private static final Logger logger = LogManager.getLogger(AccountServicesImplm.class);

    public AccountServicesImplm(AccountRepositories accountRepositories) {
        this.accountRepositories = accountRepositories;
    }

    @Override
    public Account createAccount(Account account) {
        ResponseEntity<UserDTO> userDTOResponse=restTemplate.exchange(
                User_URL + "/" + account.getUserid(), // Assuming the accountId is linked to a user in the user management microservice
              HttpMethod.GET, // HTTP method type (GET to fetch data)
                null, // No request body needed for GET
                UserDTO.class // Response type
        );

        return accountRepositories.save(account);
    }

    @Override
    public Optional<Account> getAccountById(Long id) {
        return accountRepositories.findById(id);
    }

    @Override
    public Iterable<Account> getAllAccounts() {
        return accountRepositories.findAll();
    }

    @Override
    public Optional<Account> updateAccountById(Long id,Account accountDetails) {
        Optional<Account> existingAccountOpt =  accountRepositories.findById(id);
        if (existingAccountOpt.isPresent()) {
            Account existingAccount = existingAccountOpt.get();
            existingAccount.setCreatedAt(accountDetails.getCreatedAt());
          /*  existingAccount.setAccountId(accountDetails.getAccountId());
            existingAccount.setUsername(accountDetails.getUsername());
            existingAccount.setBalance(accountDetails.getBalance());
            existingAccount.setCurrency(accountDetails.getCurrency());
            existingAccount.setAccountType(accountDetails.getAccountType());*/

            return Optional.of(accountRepositories.save(existingAccount));
        }
        return null;
    }

    @Override
    public List<Account> getAccountsByAccountTypes(List<String> accountType) {
        return accountRepositories.findByAccountTypeIn(accountType);
    }

    public void deleteAccount(Long id) {
        accountRepositories.deleteById(id);
    }

}


