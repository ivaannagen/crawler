package uk.co.allica.customer.validation;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.allica.customer.configuration.CustomerAppConfig;

@Service
@Log4j2
public class CustomerEndpointValidation {

    private final CustomerAppConfig customerAppConfig;

    @Autowired
    public CustomerEndpointValidation(CustomerAppConfig customerAppConfig) {
        this.customerAppConfig = customerAppConfig;
    }

    public void validateUrl(String address) {
    }

}