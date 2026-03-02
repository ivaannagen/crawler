package uk.co.allica.customer.api;

import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Set;

public interface CustomerApi {

    ResponseEntity<Customer> getCustomer();

    ResponseEntity<Customer> createCustomer(Customer customer);
}