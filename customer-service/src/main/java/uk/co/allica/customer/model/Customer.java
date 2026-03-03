package uk.co.allica.customer.model;

import lombok.Getter;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "customer")
@Table(name = "CUSTOMER")
@Getter
public class Customer {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Customer() {}

}