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
     @Value("${user.service.url}")
      private static String User_URL;


    @Autowired
    private final AccountRepositories accountRepositories;
    private static final Logger logger = LogManager.getLogger(AccountServicesImplm.class);

    public AccountServicesImplm(AccountRepositories accountRepositories) {
        this.accountRepositories = accountRepositories;
    }
    @Override
    public Account createAccount(Account account) {
        try {
            UserDTO user = restTemplate.getForObject(User_URL + "/" + account.getUserid(), UserDTO.class);

            if (user == null) {
                throw new RuntimeException("User not found with ID: " + account.getUserid());
            }

            // Proceed with account creation
            logger.info("Creating account for user: " + account.getUserid());
            return accountRepositories.save(account);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            logger.error("Error while fetching user details: " + e.getMessage());
            throw new RuntimeException("Error while fetching user data", e);
        }
    }
    @Override
    public Optional<Account> getAccountById(Long id) {
        try {
            UserDTO userDTO = restTemplate.getForObject(User_URL + "/" + id, UserDTO.class);

            if (userDTO == null) {
                logger.warn("User with ID: " + id + " not found");
            }

            return accountRepositories.findById(id);
        } catch (Exception e) {
            logger.error("Error fetching account or user data for ID: " + id, e);
            throw new RuntimeException("Error fetching account", e);
        }
    }


  /*  @Override
    public Account createAccount(Account account) {
        ResponseEntity<UserDTO> userDTOResponse=restTemplate.exchange(
                User_URL + "/" + account.getUserid(), // Assuming the accountId is linked to a user in the user management microservice
              HttpMethod.GET, // HTTP method type (GET to fetch data)
                null, // No request body needed for GET
                UserDTO.class // Response type
        );


        UserDTO user = userDTOResponse.getBody();

        return accountRepositories.save(account);
    }

    @Override
    public Optional<Account> getAccountById(Long id) {
        String userUrl = "http://localhost:8080/api/users/" + id;
        UserDTO userDTO = restTemplate.getForObject(userUrl, UserDTO.class);
        return accountRepositories.findById(id);

    }*/



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
    @Override
    public List<UserDTO> getLinkedAccountswithUserId(Long userid) {
            Optional<Account> account = accountRepositories.findById(userid);
                    //.orElseThrow(() -> new UserNotFoundException("User not found"));
            String url = User_URL+"?userid="+userid;
            return List.of(restTemplate.getForObject(url, UserDTO.class));

    }

    public void deleteAccount(Long id) {
        accountRepositories.deleteById(id);
    }

}


