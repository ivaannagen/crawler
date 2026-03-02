package uk.co.allica.customer.validation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.co.allica.customer.configuration.CustomerAppConfig;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerEndpointValidationTest {

    @Mock
    private CustomerAppConfig customerAppConfig;

    @InjectMocks
    private CustomerEndpointValidation underTest;

    @Before
    public void setup() {
        when(customerAppConfig.getMAX_LEVEL()).thenReturn(5);
    }

    @Test
    public void shouldPassValidationForMaxLevel() {
    }

}