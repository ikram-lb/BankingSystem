package com.banking.system.DTOS;

import com.banking.system.Enum.transactionType;
import lombok.Data;

import java.util.Date;

@Data

public class TransactionDTO {

	private Long id;
	private transactionType type;
	private double amount;
	private String Description;
	private Date date;

}
