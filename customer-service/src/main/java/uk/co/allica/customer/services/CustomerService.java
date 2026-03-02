package uk.co.allica.customer.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import uk.co.allica.customer.model.Customer;
import uk.co.allica.customer.repository.CustomerRepository;

import java.util.UUID;

@Configuration
@PropertySource("classpath:application.yml")
@Service
@Log4j2
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final UUID UUID = java.util.UUID.randomUUID();

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public UUID createCustomer(Customer customer) {
        return UUID;
    }

    public Customer getCustomer(UUID uuid) {
        return new Customer("Ivaan", "Nagen", "ivaannagen@gmail.com");
    }

}