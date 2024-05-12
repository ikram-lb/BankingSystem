package com.banking.system.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.system.Model.Transaction;

import java.util.List;

import org.springframework.data.domain.Pageable;
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
     List<Transaction> findByBankAccountCardNumber(Long cardNumber);
      Page<Transaction> findByBankAccountCardNumber(Long accId,Pageable pageable);
}
