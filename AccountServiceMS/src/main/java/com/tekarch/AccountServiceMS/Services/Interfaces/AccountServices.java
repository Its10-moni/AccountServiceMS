package com.tekarch.AccountServiceMS.Services.Interfaces;

import com.tekarch.AccountServiceMS.DTO.CombineDTO;
import com.tekarch.AccountServiceMS.DTO.UserDTO;
import com.tekarch.AccountServiceMS.Models.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountServices {
    List<Account> getAllAccounts();
    Account getAccountById(Long accountId);
    Account addAccount(Account accounts);
    void deleteAccount(Long accountId);
    Account updateAccount(Account accounts);
    Optional<BigDecimal> getAccountBalance(Long accountId);
    CombineDTO getAccountByuserId(Long userid);
    Optional<Account> updateAccountById(Long id,Account accountDetails);
     List<Account> getAccountsByAccountTypes(List<String> accountType);
   // List<Account> getLinkedAccountswithUser(Account accounts);


}
