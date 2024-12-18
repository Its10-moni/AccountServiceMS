package com.tekarch.AccountServiceMS.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDTO {

    private Integer accountId;
    private String username;
    private String accountType;
    private Double balance;
    private String currency;
    private Long userid;
    private String Email;
    private Long MobileNumber;
    private String Address;
}
