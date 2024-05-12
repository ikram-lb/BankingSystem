package com.banking.system.Controller;


import com.banking.system.DTOS.AccountDTO;
import com.banking.system.DTOS.AccountRequestDTO;
import com.banking.system.DTOS.ClientDTO;
import com.banking.system.DTOS.CreditDTO;
import com.banking.system.DTOS.DebitDTO;
import com.banking.system.DTOS.HistoryAccount;
import com.banking.system.DTOS.TransactionDTO;
import com.banking.system.DTOS.TransferDTO;
import com.banking.system.DTOS.currentAccountDTO;
import com.banking.system.DTOS.savingAccountDTO;
import com.banking.system.Exception.BankAccountNotFound;
import com.banking.system.Exception.ClientNotFoundException;
import com.banking.system.Exception.insufficientBalanceAmount;
import com.banking.system.Service.BankAccountService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:4200/")
@RestController
public class AccountRestController {


    BankAccountService bAccService;
    public AccountRestController(BankAccountService bAccServ){
    	super();
        this.bAccService=bAccServ;
    }
    
    
    //Get an account using ID
    @GetMapping("/Accounts/{accountId}")
    public AccountDTO getAccount(@PathVariable(name="accountId") Long cardNumber) throws BankAccountNotFound {
        return bAccService.getBankAccount(cardNumber);
    }
    
    //Get all the user accounts
     @GetMapping("/Accounts")
    public List<AccountDTO> listAccounts(){
        return bAccService.accountsList();
     }
     
     
     @GetMapping("/Accounts/{id}/Transactions")
     public List<TransactionDTO> getHistoryTransaction(@PathVariable(name="id") Long cardNumber){
        return bAccService.accountHistory(cardNumber);
     }
     
    @GetMapping("/Accounts/{id}/pageTransactions")
    public HistoryAccount getAccountHistoryTransaction(@PathVariable(name="id") Long cardNumber,
                                                      @RequestParam(name="Page",defaultValue="0") int page,
                                                       @RequestParam(name="size",defaultValue="5") int size) throws BankAccountNotFound{
        return bAccService.getAccountHistory(cardNumber,page,size);
    }
    
    
//    
    
    @PostMapping("/Accounts")
    public ResponseEntity<String> saveAccount(@RequestBody AccountRequestDTO accountRequest) {
        try {
            savingAccountDTO savedAccount = bAccService.saveBankSavingAccount(
                accountRequest.getInitialBalance(),
                accountRequest.getInterestRate(),
                accountRequest.getClientId());
            System.out.println("Saved account");
            return ResponseEntity.ok("Account saved successfully");
        } catch (ClientNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found with ID: " + accountRequest.getClientId());
        }
    }


    
    @PostMapping("/debit")
    public ResponseEntity<?> debit(@RequestBody DebitDTO debitDTO) {
    	
    	
    	 System.out.println("Received DebitDTO: " + debitDTO);
        // Check if accountId is null or zero
        if (debitDTO.getAccountId() == null || debitDTO.getAccountId() == 0) {
            return ResponseEntity.badRequest().body("AccountId is required and must be a non-zero value.");
        }

        try {
            this.bAccService.debit(
                debitDTO.getAccountId(),
                debitDTO.getAmount(),
                debitDTO.getDescription());

            return ResponseEntity.ok(debitDTO);
        } catch (BankAccountNotFound e) {
            // Handle BankAccountNotFound exception
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Account Not Found");
        } catch (insufficientBalanceAmount e) {
            // Handle insufficientBalanceAmount exception
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient balance.");
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during the debit transaction.");
        }
    }

    
    
    
    @PostMapping("/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws  BankAccountNotFound {
    	
        this.bAccService.credit(
        		creditDTO.getAccountId(),
        		creditDTO.getAmount(),
        		creditDTO.getDescription());
        return creditDTO;
    }
    
    @PostMapping("/transfer")
    public void transfer(@RequestBody TransferDTO transferRequestDTO) throws BankAccountNotFound, insufficientBalanceAmount {
    	
        this.bAccService.transfer(
                transferRequestDTO.getAccountSource(),
                transferRequestDTO.getAccountDestination(),
                transferRequestDTO.getAmount());
    }



}
