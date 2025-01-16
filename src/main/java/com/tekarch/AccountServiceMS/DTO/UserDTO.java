package com.tekarch.AccountServiceMS.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDTO {
    private Long userid;
    private String username;
    private String Email;
    private Long MobileNumber;
    private String Address;
}
