package com.banking.system.Model;

import com.banking.system.Enum.transactionType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

    public class Transaction {

        @Id
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        @Column(name="Id")
        private Long id;
		private transactionType type;
        private double amount;
        private String Description;
        private Date date;
        @ManyToOne
        Account bankAccount;

}
