package com.banking.system.Controller;

import com.banking.system.DTOS.AccountDTO;
import com.banking.system.DTOS.ClientDTO;
import com.banking.system.DTOS.LoginDTO;
import com.banking.system.Exception.BankAccountNotFound;
import com.banking.system.Exception.ClientNotFoundException;
import com.banking.system.Model.Client;
import com.banking.system.Outils.LoginMesage;
import com.banking.system.Service.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;
import java.util.List;
import com.banking.system.Outils.LoginMesage;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
//@RequestMapping("/Clients")
public class ClientRestController {
	
       private  BankAccountService bankAccServ ;
     
       public ClientRestController(BankAccountService bAc){
            super();
    	   this.bankAccServ=bAc;
       }
     
    // All our controller methods are in the service implementation package
        
        @GetMapping("/Clients")
//        @PreAuthorize("hasAuthority('SCOPE_USER')")
        public List<ClientDTO > getClients(){
        return bankAccServ.clientsList();
        }
        
        @GetMapping("/Clients/search")
        public List<ClientDTO > searchClients(@RequestParam(name="keyword" ,defaultValue="" )String keyword){
        return bankAccServ.searchClient(keyword);
        }
     
   // PathVariable : mean a variable that will give it as a parameter
     
        @GetMapping("/Clients/{id}")
//        @PreAuthorize("hasAuthority('SCOPE_USER')")
        public ClientDTO getClient(@PathVariable(name="id") int id) throws ClientNotFoundException{
        return bankAccServ.getClient(id);
        }
     
        @PostMapping("/Clients")
//        @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
        public ClientDTO saveClient(@RequestBody ClientDTO cltDTO){
        return   bankAccServ.saveClient(cltDTO);
        }
        
        @PutMapping("/Clients/{clientId}")
//        @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
        public ClientDTO updateClient(@PathVariable(name="clientId") int id,@RequestBody ClientDTO clientDTO){
        return bankAccServ.updateClient(clientDTO);
        }
       
        @DeleteMapping("/Clients/{id}")
//        @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
        public void deleteClient(@PathVariable int id){
        bankAccServ.deleteClient(id);
        }
//       @PostMapping(path = "/login")
//       public ResponseEntity<?> loginClient(@RequestBody LoginDTO loginDTO)
//      {
//       LoginMesage loginResponse = bankAccServ.loginClient(loginDTO);
//       return ResponseEntity.ok(loginResponse);
//       }


}
