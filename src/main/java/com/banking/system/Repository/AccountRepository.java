package com.banking.system.Repository;

import com.banking.system.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.system.Model.Account;

import java.util.List;


public interface AccountRepository extends JpaRepository<Account,Long>{
	 boolean existsByClientId(int clientId);

}
