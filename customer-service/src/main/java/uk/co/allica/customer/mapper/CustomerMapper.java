package uk.co.allica.customer.mapper;

import uk.co.allica.customer.api.CustomerRequest;
import uk.co.allica.customer.api.CustomerResponse;
import uk.co.allica.customer.model.Customer;

public class CustomerMapper {
    private CustomerMapper() {
        throw new IllegalStateException("Unable to instantiate mapper");
    }

    public static Customer map(CustomerRequest customerRequest) {
        return new Customer(
             customerRequest.getFirstName(),
             customerRequest.getLastName(),
             customerRequest.getEmail()
        );
    }

    public static CustomerResponse map(Customer customer) {
        return new CustomerResponse(
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail()
        );
    }

}