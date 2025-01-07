package com.tekarch.AccountServiceMS.Models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Entity
@Table(name = "accountservice")
@Data
@Component
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;
    @Column(name = "account_number", nullable = false, unique = true, length = 20)
    private String accountNumber;// VARCHAR(20) UNIQUE, NOT NULL

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "accountType", nullable = false)
    private String accountType;

    @Column(name = "balance", nullable = false)
    private Double balance;

    @Column(name = "currency", nullable = false)
    private String currency;
    @PrePersist
    public void prePersist()
    {
        this.createdAt= LocalDateTime.now();
    }





}



