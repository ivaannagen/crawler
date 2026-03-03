package uk.co.allica.customer.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uk.co.allica.customer.exceptions.CustomerException;
import uk.co.allica.customer.model.Customer;
import uk.co.allica.customer.repository.CustomerRepository;

import java.util.Optional;
import java.util.UUID;

@Configuration
@PropertySource("classpath:application.yml")
@Service
@Log4j2
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public UUID createCustomer(Customer customer) {
        validateEmail(customer.getEmail());
        Customer createdCustomer = customerRepository.save(customer);
        UUID id = createdCustomer.getId();
        log.info("Successfully created Customer with id: [{}]", id);
        return id;
    }

    public Optional<Customer> getCustomer(UUID id) {
        return customerRepository.findById(id);
    }

    private void validateEmail(String email) {
        if(customerRepository.existsCustomerByEmail(email)) {
            throw new CustomerException(HttpStatus.CONFLICT, "Email already in use");
        }
    }

}