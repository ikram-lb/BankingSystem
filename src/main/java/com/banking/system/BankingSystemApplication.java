package com.banking.system;

//
//import com.banking.system.DTOS.AccountDTO;
//import com.banking.system.DTOS.ClientDTO;
//import com.banking.system.DTOS.currentAccountDTO;
//import com.banking.system.DTOS.savingAccountDTO;
//import com.banking.system.Enum.statusType;
//import com.banking.system.Enum.transactionType;
//import com.banking.system.Exception.ClientNotFoundException;
//
//
//import com.banking.system.Outils.AutoValueGenerator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
import com.banking.system.Repository.AccountRepository;
import com.banking.system.Repository.ClientRepository;
import com.banking.system.Repository.TransactionRepository;
import com.banking.system.Service.BankAccountService;
//import com.banking.system.Service.BankAccountService;
import com.banking.system.mapper.AccountMapperImp;
import com.banking.system.DTOS.AccountDTO;
import com.banking.system.DTOS.ClientDTO;
import com.banking.system.DTOS.currentAccountDTO;
import com.banking.system.DTOS.savingAccountDTO;
import com.banking.system.Enum.statusType;
import com.banking.system.Enum.transactionType;
import com.banking.system.Exception.ClientNotFoundException;
import com.banking.system.Model.Client;
import com.banking.system.Model.CurrentAccount;
import com.banking.system.Model.SavingAccount;
import com.banking.system.Model.Transaction;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;




@SpringBootApplication
public class BankingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingSystemApplication.class, args);}

	
	
//	@Bean
//	public WebMvcConfigurer configure() {
//		return new WebMvcConfigurer() {
//		@Override
//		public void addCorsMappings(CorsRegistry registry) {
//			registry.addMapping("/*").allowedOrigins("http://localhost:4200");
//		}
//		};
//	}
	
    //   @Bean
        CommandLineRunner commandLineRunner(BankAccountService bankAccountService,AccountMapperImp accMap){
        return args -> {
        	
        	// this is for adding new client into my database (table : client)//
        	Stream.of("Ikram","Marouane","Soufiane").forEach(name->{
        	   ClientDTO customer=new ClientDTO();
               customer.setFirstName(name);
               customer.setLastName("Lablaida");
               customer.setPassword(name);
               customer.setEmail(name+"@gmail.com");
               customer.setAddress("Kelaa des Sraghna");
//               bankAccountService.saveClient(customer);
        
           });
           
           
        	// for every CLIENT create new Account for it (table: Account)// 
             bankAccountService.clientsList().forEach(customer->{
              try {
                  bankAccountService.saveBankCurrentAccount(Math.random()*900,9000,customer.getId());
                 bankAccountService.saveBankSavingAccount(Math.random()*1200,5.5,customer.getId());
                 
             } catch (ClientNotFoundException e) {
                   e.printStackTrace();
               }
            });
           //comment the debit and credit operation //
//           
//            List<AccountDTO> bankAccounts = bankAccountService.accountsList();
//            for (AccountDTO bankAccount:bankAccounts){
//               for (int i = 0; i <10 ; i++) {
//                   Long accountId;
//                   if(bankAccount instanceof savingAccountDTO){
//                       accountId=((savingAccountDTO) bankAccount).getCardNumber();
//                   } else{
//                       accountId=((currentAccountDTO) bankAccount).getCardNumber();
//                   }
//                   bankAccountService.credit(accountId,1+Math.random()*2,"Credit");
//                   bankAccountService.debit(accountId,1+Math.random()*1,"Debit");
//               }
//            }
            };
            }
       


     
       CommandLineRunner start(ClientRepository customerRepository,
               AccountRepository bankAccountRepository,
               TransactionRepository accountOperationRepository){
return args -> {
//Stream.of("Hasswsan","Yassinkloase","Aisscha").forEach(name->{
//  Client c1=new Client(1,"dddd","vvvw","ddd","ffffw","dddw","dddw","dddi","mm");
//   customerRepository.save(c1);
//});
//customerRepository.findAll().forEach(cust->{
//   CurrentAccount currentAccount=new CurrentAccount();
////   currentAccount.setId(UUID.randomUUID());
//   currentAccount.setBalance(Math.random()*90000);
//   currentAccount.setOpeningDate(new Date());
//   currentAccount.setStatus(statusType.Activated);
//   currentAccount.setClient(cust);
//   currentAccount.setOverDraft(9000);
//   bankAccountRepository.save(currentAccount);
//
   customerRepository.findAll().forEach(cust->{
   SavingAccount savingAccount=new SavingAccount();
   savingAccount.setBalance(Math.random()*90);
   savingAccount.setOpeningDate(new Date());
   savingAccount.setStatus(statusType.Activated);
   savingAccount.setClient(cust);
//   savingAccount.setClient(cust);
   savingAccount.setInterestRate(5.5);
   bankAccountRepository.save(savingAccount);
	 }
	//
	);
	};
//
//bankAccountRepository.findAll().forEach(acc->{
//   for (int i = 0; i <10 ; i++) {
//       Transaction accountOperation=new Transaction();
//       accountOperation.setDate(new Date());
//       accountOperation.setAmount(Math.random()*12000);
//       accountOperation.setType(Math.random()>0.5? transactionType.DePit: transactionType.Credit);
//       accountOperation.setBankAccount(acc);
//       accountOperationRepository.save(accountOperation);
//   }
//
//});
//};
//        
   
//		        }
}}
		        







