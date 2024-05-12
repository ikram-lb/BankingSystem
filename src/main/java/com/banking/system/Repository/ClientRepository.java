package com.banking.system.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.banking.system.Model.Client;
import java.util.Optional;
import java.util.List;


public interface ClientRepository extends JpaRepository<Client, Integer> {

    Optional<Client> findByEmailAndPassword(String email, String password);
    Client findByEmail(String email);
    List<Client> findByFirstNameContains(String keyword);

}
