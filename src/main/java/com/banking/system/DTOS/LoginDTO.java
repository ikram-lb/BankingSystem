package com.banking.system.DTOS;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class LoginDTO {
        private String email;
        private String password;


}
