package uk.co.allica.customer.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.co.allica.customer.mapper.CustomerMapper;
import uk.co.allica.customer.model.Customer;
import uk.co.allica.customer.services.CustomerService;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("customer")
public class CustomerController implements CustomerApi {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    @GetMapping("{uuid}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable UUID uuid) {
        Optional<Customer> customerOpt = customerService.getCustomer(uuid);
        return customerOpt.map(customer -> ResponseEntity.ok(CustomerMapper.map(customer)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @PostMapping
    public ResponseEntity<UUID> createCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        UUID customerUUID = customerService.createCustomer(CustomerMapper.map(customerRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(customerUUID);
    }

}