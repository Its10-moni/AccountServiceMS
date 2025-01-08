package com.tekarch.AccountServiceMS.DTO;

import com.tekarch.AccountServiceMS.Models.Account;
import lombok.Data;

@Data
public class CombineDTO {
    private UserDTO user;
    private Account account;
}
