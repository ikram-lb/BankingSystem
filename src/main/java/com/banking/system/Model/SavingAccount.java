package com.banking.system.Model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("SavingAccount")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class SavingAccount extends Account{

	private double interestRate;

}
