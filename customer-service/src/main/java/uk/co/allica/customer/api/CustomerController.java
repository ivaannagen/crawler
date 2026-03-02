package uk.co.allica.customer.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.allica.customer.services.CustomerService;
import uk.co.allica.customer.validation.CustomerEndpointValidation;


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
    @GetMapping
//    @RequestParam(name = "address") String address, @RequestParam(defaultValue = "1", name = "maxLevel") Integer maxLevel
    public ResponseEntity<Customer> getCustomer() {

        return ResponseEntity.ok(null);
    }

    @Override
    public ResponseEntity<Customer> createCustomer(Customer customer) {
        return ResponseEntity.ok(null);
    }

}