package uk.co.allica.customer.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.allica.customer.services.CustomerService;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerApiTest {

    @MockBean
    private CustomerService customerService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerApi apiUnderTest;

    @Test
    void refreshCustomer() {
        ResponseEntity<Customer> response = apiUnderTest.getCustomer();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Customer customer = response.getBody();
        assertThat(customer).isNotNull();
    }

    @Test
    void getVisitedInvalidUrl() {
//        assertThatThrownBy(() -> apiUnderTest.getCustomer()
//        .isInstanceOf(CustomerException.class)
//        .hasMessageContaining("Url to crawl is invalid")
//        .hasMessageContaining("400 BAD_REQUEST ");
    }

}