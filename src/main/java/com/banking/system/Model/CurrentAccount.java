package com.banking.system.Model;

import jakarta.persistence.DiscriminatorValue;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("CurrentAccount")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class CurrentAccount extends Account{
	private double overDraft;
}
