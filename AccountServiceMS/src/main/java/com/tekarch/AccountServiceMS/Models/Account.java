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
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "accountType", nullable = false)
    private String accountType;





}



