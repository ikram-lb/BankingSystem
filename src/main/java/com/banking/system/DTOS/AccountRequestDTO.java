package com.banking.system.DTOS;

import lombok.Data;

@Data
public class AccountRequestDTO {

	private double initialBalance;
    private double interestRate;
    private int clientId;

}
