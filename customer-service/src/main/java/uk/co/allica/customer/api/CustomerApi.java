package uk.co.allica.customer.api;

import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface CustomerApi {

    ResponseEntity<CustomerResponse> getCustomer(UUID uuid);

    ResponseEntity<UUID> createCustomer(CustomerRequest customerRequest);
}