package com.tekarch.AccountServiceMS.Services.Interfaces;

import com.tekarch.AccountServiceMS.DTO.UserDTO;
import com.tekarch.AccountServiceMS.Models.Account;

import java.util.List;
import java.util.Optional;

public interface AccountServices {
    Account createAccount(Account account);
  // Optional<Account> getAccountByuserId(Long userid);
    Optional<Account> getAccountByaccounId(Long accountId);
    Account updateAccount(Account account);
    Iterable<Account> getAllAccounts();
   // Optional<Account> updateAccountById(Long id,Account accountDetails);
    List<Account> getAccountsByAccountTypes(List<String> accountType);
    List<UserDTO> getLinkedAccountswithUserId(Long userid);
    default void deleteAccount(Long userid) {

    }

}
