package com.banking.system.Model;


import com.banking.system.Enum.statusType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;



@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="AccountType",length = 14)

public class Account {

     @Id
     @GeneratedValue(strategy=GenerationType.IDENTITY)
     @Column(name="cardNumber")
      private  Long cardNumber;
      private double Balance;
      private Date openingDate;
//    @Enumerated(EnumType.STRING)
      private statusType status;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id") // Adjust the column name based on your database schema
    Client client ;
    @OneToMany(mappedBy = "bankAccount",fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    List <Transaction> transactionList;

    
    public Long getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(Long cardNumber) {
		this.cardNumber = cardNumber;
	}
	public double getBalance() {
		return Balance;
	}
	public void setBalance(double balance) {
		Balance = balance;
	}
	public Date getOpeningDate() {
		return openingDate;
	}
	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
	}
	public statusType getStatus() {
		return status;
	}
	public void setStatus(statusType status) {
		this.status = status;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public List<Transaction> getTransactionList() {
		return transactionList;
	}
	public void setTransactionList(List<Transaction> transactionList) {
		this.transactionList = transactionList;
	}
	
}






