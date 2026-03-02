package uk.co.allica.customer.repository;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CustomerRepository extends AbstractCRUD<String, Set<String>> {
}