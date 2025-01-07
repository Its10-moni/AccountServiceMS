package com.tekarch.AccountServiceMS.Repositories;

import com.tekarch.AccountServiceMS.DTO.UserDTO;
import com.tekarch.AccountServiceMS.Models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepositories extends JpaRepository<Account,Long> {
   // UserDTO findByUsername(String username);
    List<Account> findByAccountTypeIn(List<String> accountType);
   // Optional<Account> findByAccountIdAndUserId(Long accountId, Long userid);
}
