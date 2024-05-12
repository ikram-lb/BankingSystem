package com.banking.system.DTOS;

import lombok.Data;

import java.util.List;

@Data
public class HistoryAccount {

    private Long accountId;
    private double balance ;
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private List<TransactionDTO> accountTransactionDTOS;

	
}
