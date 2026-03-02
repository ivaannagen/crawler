package uk.co.allica.customer.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.co.allica.customer.repository.CustomerRepository;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService underTest;


    @Test
    public void shouldSuccessfullyFetchUrlsToVisit() {

    }

    @Test
    public void shouldCrawlNoUrls() {

    }

    @Test
    public void shouldSuccessfullyCrawlUrls() {

    }

    @Test
    public void shouldSuccessfullyCrawlUrlsWithoutDuplicates() {

    }

}