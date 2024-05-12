package com.banking.system.Service;

import com.banking.system.DTOS.*;
import com.banking.system.Exception.BankAccountNotFound;
import com.banking.system.Exception.ClientNotFoundException;
import com.banking.system.Exception.insufficientBalanceAmount;
import com.banking.system.Model.Client;
import com.banking.system.Outils.LoginMesage;
//import com.banking.system.Outils.SecurityConfig;
import com.banking.system.Model.CurrentAccount;

import java.util.List;

public interface  BankAccountService {

     //Create a client
    //Client saveClient(Client C1);
    ClientDTO saveClient(ClientDTO client);
//    String addClient(ClientDTO cltdto);
    ClientDTO getClient(int clientId) throws ClientNotFoundException;
    ClientDTO updateClient(ClientDTO cltDTO);
    void deleteClient(int clientId);
    List<ClientDTO> clientsList();
    List<ClientDTO> searchClient(String keyword);

    //   Create  a bank account for this client
    currentAccountDTO saveBankCurrentAccount(double initialBalance,double overdraft, int clientId) throws ClientNotFoundException;
    savingAccountDTO saveBankSavingAccount (double initialBalance, double interestRate, int clientId) throws ClientNotFoundException;
//   savingAccountDTO saveBankSavingAccount(AccountRequestDTO accountRequest);
    AccountDTO getBankAccount(Long accountId) throws BankAccountNotFound;
    List<AccountDTO> accountsList();
    // List of operation
//    we can say that  every bank transaction it is just a credit or debit of the account
//    Debit of account meaning : withdrawal in as money
//    Credit of an account : deposit in an account
    void debit(Long accountId,double amount,String transactionDescription) throws BankAccountNotFound, insufficientBalanceAmount;
    void credit(Long accountId,double amount,String transactionDescription) throws BankAccountNotFound;
    void transfer(Long accountIdSource,Long accountIdDestination,double amount) throws BankAccountNotFound, insufficientBalanceAmount;

//    ClientDTO getCustomer(Long clientId) throws ClientNotFoundException;
//    List<TransactionDTO> listTransaction();
    List<TransactionDTO> accountHistory(Long accId);
    HistoryAccount getAccountHistory(Long Id, int page, int size) throws BankAccountNotFound;
//    LoginMesage loginClient(LoginDTO loginDTO);
//
}
