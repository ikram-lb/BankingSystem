package com.banking.system.mapper;

import com.banking.system.DTOS.ClientDTO;
import com.banking.system.DTOS.TransactionDTO;
import com.banking.system.DTOS.currentAccountDTO;
import com.banking.system.DTOS.savingAccountDTO;
import com.banking.system.Model.Client;
import com.banking.system.Model.CurrentAccount;
import com.banking.system.Model.SavingAccount;
import com.banking.system.Model.Transaction;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class AccountMapperImp {

       // Translate from Entity client to client DTO
        public ClientDTO ToClientDTO(Client client){
    	
    	if (client == null) {
        //  Handle the null case, depending on your requirements.
            return null; // Or create an empty ClientDTO, or throw an exception, etc.
        }
        ClientDTO clientDTO=new ClientDTO();
        BeanUtils.copyProperties(client,clientDTO);
        return  clientDTO;
        }

        // Translate from client DTO to Entity client
        public Client ToClient(ClientDTO cltDTO){
        Client clt=new Client();
        BeanUtils.copyProperties(cltDTO,clt);
        return  clt;
        }

       // Translate from Entity savingAccount to savingAccountDTO
        public savingAccountDTO toSavingAccountDTO(SavingAccount svAccount){
        savingAccountDTO svAccountDTO=new savingAccountDTO();
        BeanUtils.copyProperties(svAccount,svAccountDTO);
        svAccountDTO.setClientDTO(ToClientDTO(svAccount.getClient()));
        svAccountDTO.setType(svAccount.getClass().getSimpleName());
        return svAccountDTO;
        }
     
       // Translate from Entity savingAccount to savingAccountDTO
        public SavingAccount toSavingAccount(savingAccountDTO savingBankAccountDTO){

        SavingAccount savingAccount=new SavingAccount();
        BeanUtils.copyProperties(savingBankAccountDTO,savingAccount);
        savingAccount.setClient(ToClient(savingBankAccountDTO.getClientDTO()));
        return savingAccount;

         }
    
        public currentAccountDTO toCurrentAccountDTO(CurrentAccount crAccount){

        currentAccountDTO crAccountDTO=new currentAccountDTO();
        BeanUtils.copyProperties(crAccount,crAccountDTO);
        crAccountDTO.setClientDTO(ToClientDTO(crAccount.getClient()));
        // Add an attribute with the name of type that stock the name of the class
        crAccountDTO.setType(crAccount.getClass().getSimpleName());
        return crAccountDTO;
         }
    
        public CurrentAccount toCurrentAccount(currentAccountDTO crAccountDTO){
        CurrentAccount crAccount=new CurrentAccount();
        BeanUtils.copyProperties(crAccountDTO,crAccount);
        crAccount.setClient(ToClient(crAccountDTO.getClientDTO()));
        return crAccount;
         }


        public TransactionDTO toTransactionDTO(Transaction accTr){
        TransactionDTO accTrDTO=new TransactionDTO();
        BeanUtils.copyProperties(accTr,accTrDTO);
        return accTrDTO;
         }



}
