package com.tekarch.AccountServiceMS.Repositories;

import com.tekarch.AccountServiceMS.DTO.UserDTO;
import com.tekarch.AccountServiceMS.Models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepositories extends JpaRepository<Account,Long> {
   // UserDTO findByUsername(String username);
   Optional<BigDecimal> findBalanceByAccountId(Long accountId);
    List<Account> findByAccountTypeIn(List<String> accountType);

    Optional<Account> findByUserid(Long userid);
}
