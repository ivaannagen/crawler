package uk.co.allica.customer.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.co.allica.customer.mapper.CustomerMapper;
import uk.co.allica.customer.model.Customer;
import uk.co.allica.customer.services.CustomerService;
import uk.co.allica.customer.validation.CustomerEndpointValidation;

import javax.validation.Valid;
import java.util.UUID;


@RestController
@RequestMapping("customer")
public class CustomerController implements CustomerApi {

    private final CustomerEndpointValidation customerEndpointValidation;

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerEndpointValidation customerEndpointValidation, CustomerService customerService) {
        this.customerEndpointValidation = customerEndpointValidation;
        this.customerService = customerService;
    }


    @Override
    @GetMapping("{uuid}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable UUID uuid) {
        Customer customer = customerService.getCustomer(uuid);
        return ResponseEntity.ok(CustomerMapper.map(customer));
    }

    @Override
    @PostMapping
    public ResponseEntity<UUID> createCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        UUID customerUUID = customerService.createCustomer(CustomerMapper.map(customerRequest));
        return ResponseEntity.ok(customerUUID);
    }

}