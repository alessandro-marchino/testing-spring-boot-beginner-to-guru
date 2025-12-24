package guru.springframework.brewery.web.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Sort;

import guru.springframework.brewery.domain.Customer;
import guru.springframework.brewery.domain.CustomerRepository;
import guru.springframework.brewery.web.model.BeerOrderPagedList;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
public class BeerOrderControllerIT {

    @Autowired TestRestTemplate restTemplate;
    @Autowired CustomerRepository customerRepository;

    Customer customer;

    @BeforeEach
    void setUp() {
        customer = customerRepository.findAll(Sort.by("id")).getFirst();
    }

    @Test
    void testListBeers() {
        // Given
        // When
        BeerOrderPagedList result = restTemplate.getForObject("/api/v1/customers/{customerId}/orders",
            BeerOrderPagedList.class,
            customer.getId());
        // Then
        assertThat(result.getContent()).hasSize(1);
    }
}
