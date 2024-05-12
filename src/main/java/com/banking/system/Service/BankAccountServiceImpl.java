package com.banking.system.Service;

import com.banking.system.DTOS.ClientDTO;
import com.banking.system.DTOS.*;
import com.banking.system.Enum.transactionType;
import com.banking.system.Exception.BankAccountNotFound;
import com.banking.system.Exception.ClientNotFoundException;
import com.banking.system.Exception.insufficientBalanceAmount;
import com.banking.system.Model.*;
import com.banking.system.Outils.LoginMesage;
import com.banking.system.Repository.AccountRepository;
import com.banking.system.Repository.ClientRepository;
import com.banking.system.Repository.TransactionRepository;
import com.banking.system.mapper.AccountMapperImp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SuppressWarnings("ControlFlowStatementWithoutBraces")
@Service
@Transactional
@Data
@Slf4j
@AllArgsConstructor

public class BankAccountServiceImpl implements BankAccountService{

    private ClientRepository cRepo;

    private AccountRepository accRepo;

    private TransactionRepository trRepo;

    private AccountMapperImp accMap;


//    private PasswordEncoder passwordEncoder;

    @Override
    public ClientDTO saveClient(ClientDTO clltDTO){

   //   log.info("Saving new client");
        Client Clt=accMap.ToClient(clltDTO);
        Client savedClient=cRepo.save(Clt);
        return accMap.ToClientDTO(savedClient);
    }

    @Override
    public ClientDTO getClient(int id) throws ClientNotFoundException {
        Client clt = cRepo.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("User Not found"));
        return accMap.ToClientDTO(clt);
    }


    @Override
    public ClientDTO updateClient(ClientDTO cltDTO){
//        log.info("Upd ating new Account");
        Client clt=accMap.ToClient(cltDTO);
        Client savedClient=cRepo.save(clt);
        return accMap.ToClientDTO(savedClient);
    }
    @Override
    public void deleteClient(int clientId) throws ClientNotFoundException {
        try {
            cRepo.deleteById(clientId);
        } catch (EmptyResultDataAccessException ex) {
            // Handle the case where the client with the given ID does not exist.
            throw new ClientNotFoundException("Client with ID " + clientId + " not found");
        }
    }

    @Override
    public List<ClientDTO> searchClient(String keyword) {
        List<Client> clt=cRepo.findByFirstNameContains(keyword);
        List<ClientDTO> cltdto=clt.stream().map(c->accMap.ToClientDTO(c)).collect(Collectors.toList());
        return cltdto;
    }

    @Override
    public List<ClientDTO> clientsList() {
        //   functional programing
        List<Client> Clients = cRepo.findAll();
        //  Stream map Collect
        List<ClientDTO> clientDTOS = Clients.stream()
                .map(client -> accMap.ToClientDTO(client))
                .collect(Collectors.toList());
        return clientDTOS;
    }

    @Override
    public currentAccountDTO saveBankCurrentAccount(double initialBalance, double overdraft, int clientId) throws ClientNotFoundException {

        Client client = cRepo.findById(clientId).orElse(null);
//    	Transaction debitTr=trRepo.findby;

        if (client == null)
            throw new ClientNotFoundException("Client not found with ID: " + clientId);

        CurrentAccount newAcc = new CurrentAccount();
        newAcc.setOpeningDate(new Date());
        newAcc.setBalance(initialBalance);
        newAcc.setOverDraft(overdraft);
        newAcc.setClient(client);
        newAcc.setTransactionList(null);
        CurrentAccount newAccRepo = accRepo.save(newAcc);
        return accMap.toCurrentAccountDTO(newAccRepo);
    }

    @Override
    public savingAccountDTO saveBankSavingAccount(double initialBalance, double interestRate, int clientId) throws ClientNotFoundException {
        Client client = cRepo.findById(clientId).orElse(null);

        if (client == null) {
            throw new ClientNotFoundException("Client not found with ID: " + clientId);
        }

        SavingAccount newAcc = new SavingAccount();
        newAcc.setOpeningDate(new Date());
        newAcc.setBalance(initialBalance);
        newAcc.setInterestRate(interestRate); // Use the provided interest rate
        newAcc.setClient(client);
        SavingAccount newAccRepo = accRepo.save(newAcc);
        return accMap.toSavingAccountDTO(newAccRepo);
    }

    @Override
    public AccountDTO getBankAccount(Long accountId) throws BankAccountNotFound {

        Account accUser=accRepo.findById(accountId)
                .orElseThrow(()->new BankAccountNotFound("User Account Not Found"));
        if(accUser instanceof SavingAccount) {
            savingAccountDTO savingAccountDTO = accMap.toSavingAccountDTO((SavingAccount) accUser);
            return savingAccountDTO;
        } else{
            currentAccountDTO currentAccountDTO = accMap.toCurrentAccountDTO((CurrentAccount) accUser);
            return currentAccountDTO;}
    }

    @Override
    public void debit(Long accountId, double amount, String transactionDescription) throws BankAccountNotFound, insufficientBalanceAmount {

        if (accountId == null) {
            throw new IllegalArgumentException("Account ID cannot be null.");
        }

        // Select the account based on the account ID
        Account accUser = accRepo.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFound("User Account Not Found"));

        // Check if the account balance is sufficient
        if (accUser.getBalance() < amount) {
            throw new insufficientBalanceAmount("Sorry, this transaction is impossible due to insufficient balance.");
        }

        try {
            // Create a new Transaction record
            Transaction debitTr = new Transaction();
            debitTr.setAmount(amount);
            debitTr.setDate(new Date());
            debitTr.setType(transactionType.DePit);
            debitTr.setBankAccount(accUser);
            debitTr.setDescription(transactionDescription);

            // Save the Transaction record
            trRepo.save(debitTr);

            // Update the account balance
            double theNewBalance = accUser.getBalance() - amount;
            accUser.setBalance(theNewBalance);

            // Save the updated account
            accRepo.save(accUser);
        } catch (Exception e) {
            // Handle any unexpected exceptions and log them
            e.printStackTrace();
            throw new RuntimeException("An error occurred during the debit transaction.");
        }
    }

    @Override
    public void credit(Long accountId, double amount, String transactionDescription) throws BankAccountNotFound {

        //Select the account based on the account
        // Deposit the amount from the balance account

        //log.info(" This a Credit transaction ");

        Account accUser=accRepo.findById((long) accountId)
                .orElseThrow(()->new BankAccountNotFound("User Account Not Found"));

//        if (accountId == null) {
//            throw new IllegalArgumentException("Account ID must not be null.");
//        }

        // Saved our transaction data int our repository

        Transaction creditTrAcc=new Transaction();
        creditTrAcc.setAmount(amount);
        creditTrAcc.setDate(new Date());
        creditTrAcc.setType(transactionType.Credit);
        creditTrAcc.setBankAccount(accUser);
        creditTrAcc.setDescription(transactionDescription);
        trRepo.save(creditTrAcc);
        double theNewBalance=accUser.getBalance()+amount;
        accUser.setBalance(theNewBalance);

    }


    @Override
    public void transfer(Long accountIdSource, Long accountIdDestination, double amount) throws BankAccountNotFound, insufficientBalanceAmount {

        // transfer from account source that mean we debit the account
        debit(accountIdSource,amount,"Transfer to "+accountIdDestination);
        //the destination account receive money means it is a dePit transaction
        credit(accountIdDestination,amount,"transfer from "+accountIdSource);

    }
    @Override
    public List<AccountDTO> accountsList(){

        List<Account> bankAccounts = accRepo.findAll();
        List<AccountDTO> bankAccountDTOS = bankAccounts.stream().map(bankAccount -> {

            if (bankAccount instanceof SavingAccount) {
                SavingAccount savingAccount = (SavingAccount) bankAccount;
                return accMap.toSavingAccountDTO(savingAccount);}
            else {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return accMap.toCurrentAccountDTO(currentAccount);
            }
        }).collect(Collectors.toList());

        return bankAccountDTOS;
    }

    @Override
    public List<TransactionDTO> accountHistory(Long accId){
        List<Transaction> transactionByAccountId = trRepo.findByBankAccountCardNumber(accId);
        List<TransactionDTO> listOperation = transactionByAccountId.stream()
                .map(tr -> accMap.toTransactionDTO(tr))
                .collect(Collectors.toList());
        return listOperation;
    }


    @Override
    public HistoryAccount getAccountHistory(Long Id, int page, int size) throws BankAccountNotFound{

        Account bankAcc=accRepo.findById(Id).orElse(null);
        if(bankAcc==null) throw new BankAccountNotFound("Bank Account Not Found");
        Page<Transaction> History=trRepo.findByBankAccountCardNumber(Id, PageRequest.of(page,size));
        HistoryAccount historyAccDTO=new HistoryAccount();
        List<TransactionDTO> trDTO = History.getContent().stream().map(op -> accMap.toTransactionDTO(op)).collect(Collectors.toList());
        historyAccDTO.setAccountTransactionDTOS(trDTO);
        historyAccDTO.setAccountId(bankAcc.getCardNumber());
        historyAccDTO.setBalance(bankAcc.getBalance());
        historyAccDTO.setCurrentPage(page);
        historyAccDTO.setPageSize(size);
        return historyAccDTO;
    }


//    @Override
//    public String addClient(ClientDTO cltdto) {
//        Client client = new Client(
//                cltdto.getId(),
//                cltdto.getFirstName(),
//                cltdto.getLastName(),
//                cltdto.getAddress(),
//                cltdto.getDob(),
//                cltdto.getPhoneNumber(),
//                cltdto.getGender(),
//                cltdto.getEmail(),
//                cltdto.getPassword()
//
////                this.passwordEncoder.encode(cltdto.getPassword())
//
//        );
//        cRepo.save(client);
//        return client.getFirstName();
//    }

//    @Override
//    public currentAccountDTO saveBankCurrentAccount(double initialBalance,double overdraft, int clientId) throws ClientNotFoundException {
//
////           log.info("Saving new Account");
//           Client client=cRepo.findById(clientId).orElse(null);
//          
//            if(client==null)
//            throw new ClientNotFoundException("this client not found ");
//      
//                CurrentAccount newAcc=new CurrentAccount();
////                 newAcc.setCardNumber((UUID.randomUUID()));
//                 newAcc.setOpeningDate(new Date());
//                 newAcc.setBalance(initialBalance);
//                 newAcc.setOverDraft(overdraft);
//                  CurrentAccount  newAccRepo= accRepo.save(newAcc);
//                  System.out.println(clientId);
//                  System.out.println("Save bank current account");
//             return accMap.toCurrentAccountDTO(newAccRepo);
//           }


    
//  @Override
//    public savingAccountDTO saveBankSavingAccount(double initialBalance,double interestRate, int clientId) throws ClientNotFoundException {
////        log.info("Saving new Account");
//        
//    	Client client=cRepo.findById(clientId).orElse(null);
//        
//    	if(client==null)
//            throw new ClientNotFoundException("this client not found ");
//
//        SavingAccount newAcc=new SavingAccount();
//        newAcc.setOpeningDate(new Date());
//        newAcc.setBalance(initialBalance);
//        newAcc.setInterestRate(interestRate);
//        SavingAccount newAccRepo= accRepo.save(newAcc);
//          
//    return accMap.toSavingAccountDTO(newAccRepo);
//    }

    
//   public savingAccountDTO saveBankSavingAccount(AccountRequestDTO accountRequest) throws ClientNotFoundException {
//	  Client client = cRepo.findById(accountRequest.getClientId())
//              .orElseThrow(() -> new ClientNotFoundException("Client not found"));
//
//	    if (client == null) {
//	        throw new ClientNotFoundException("This client was not found");
//	    }
//	    SavingAccount newAcc = new SavingAccount();
//	    newAcc.setOpeningDate(new Date());
//	    newAcc.setBalance(accountRequest.getInitialBalance());
//	    newAcc.setInterestRate(accountRequest.getInterestRate());
//	    newAcc.setClient(client);
//	    SavingAccount savedAcc = accRepo.save(newAcc);
//	    return accMap.toSavingAccountDTO(savedAcc);
//	}
  







//    @Override
//    public void debit(Long accountId, double amount,String transactionDescription) throws BankAccountNotFound, insufficientBalanceAmount {
//          //Select the account based on the account
//         // withdrawal the amount from the balance account
//        //        log.info(" This a debit transaction ");
//        Account accUser=accRepo.findById((long) accountId)
//                        .orElseThrow(()->new BankAccountNotFound("User Account Not Found"));
//
////        if ((long)accountId == null) {
////            throw new IllegalArgumentException("Account ID must not be null.");
////        }
//        
//        if(accUser.getBalance()<amount)
//                  throw new insufficientBalanceAmount(" Sorry this transaction impossible ,Balance insufficient ");
//        // Saved our transaction data int our repository
//
//        Transaction debitTr=new Transaction();
//        debitTr.setAmount(amount);
//        debitTr.setDate(new Date());
//        debitTr.setType(transactionType.DePit);
//        debitTr.setBankAccount(accUser);
//        debitTr.setDescription(transactionDescription);
//        trRepo.save(debitTr);
//        double theNewBalance=accUser.getBalance()-amount;
//        accUser.setBalance(theNewBalance);
//
//    }
    

    
    
//    @Override
//    public void deleteClient(int clientId){
//        cRepo.deleteById(clientId);
//    }
    


    
   



 
  
  
//  @Override
//    public LoginMesage  loginClient(LoginDTO logDTO){
//      Client clt1 = ClientRepository.findByEmail(logDTO.getEmail());
//      if (clt1 != null) {
//          String password = logDTO.getPassword();
//          String encodedPassword = clt1.getPassword();
//          Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
//          if (isPwdRight) {
//              Optional<Client> client = ClientRepository.findOneByEmailAndPassword(logDTO.getEmail(), encodedPassword);
//              if (client.isPresent()) {
//                  return new LoginMesage("Login Success", true);
//              } else {
//                  return new LoginMesage("Login Failed", false);
//              }
//          } else {
//              return new LoginMesage("password Not Match", false);
//          }
//      }else {
//          return new LoginMesage("Email not exits", false);
//      }
//  }


//
//   @Override
//   public savingAccountDTO saveBankSavingAccount(double initialBalance, double interestRate, int clientId) throws ClientNotFoundException {
//	
//	Client client = cRepo.findById(clientId).orElse(null);
//
//    if (client == null)
//        throw new ClientNotFoundException("Client not found with ID: " + clientId);
//
//    SavingAccount newAcc = new SavingAccount();
//    newAcc.setOpeningDate(new Date());
//    newAcc.setBalance(initialBalance);
//    newAcc.setInterestRate(5.5);
//    newAcc.setClient(client);
//    SavingAccount newAccRepo = accRepo.save(newAcc);
//    return accMap.toSavingAccountDTO(newAccRepo);
//	
//  }
  
  


  }


