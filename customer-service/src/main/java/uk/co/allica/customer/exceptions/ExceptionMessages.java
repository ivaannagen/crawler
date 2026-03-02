package uk.co.allica.customer.exceptions;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessages {

    public static final String CUSTOMER_EXCEPTION_MESSAGE = "Failed to retrieve customer data with message: [%s]";

}