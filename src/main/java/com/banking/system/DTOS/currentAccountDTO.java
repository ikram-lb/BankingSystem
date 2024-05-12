package com.banking.system.DTOS;

import com.banking.system.Enum.statusType;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


import java.util.Date;


@Data
public class currentAccountDTO extends AccountDTO{
	

	private  Long cardNumber;
    private double balance;
    private Date openingDate;
    private statusType status;
    private ClientDTO clientDTO ;
    private double overDraft; 
	

	
}
