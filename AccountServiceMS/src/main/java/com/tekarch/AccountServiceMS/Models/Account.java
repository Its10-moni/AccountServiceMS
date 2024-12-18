package com.tekarch.AccountServiceMS.Models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Table(name = "account")
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;

    @Column(name = "accountId", nullable = false)
    private Integer accountId;

    @Column(name = "username", nullable = false, unique = true)
    @NotEmpty(message = "Name should not be empty")
    private String username;


    @Column(name = "accountType", nullable = false)
    private String accountType;

    @Column(name = "balance", nullable = false)
    private Double balance;

    @Column(name = "currency", nullable = false)
    private String currency;



}



