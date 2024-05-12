package com.banking.system.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Client {


	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="Id")
    private int id;
    private String password;
    private String firstName;
    private String lastName;
    private String dob;
    private String Address;
    private String phoneNumber;
    private String email;
    private String gender;
    @OneToMany(mappedBy = "client",cascade = CascadeType.ALL)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    List<Account>  Accounts;




}








