package guru.springframework.brewery.web.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import guru.springframework.brewery.web.model.BeerPagedList;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
class BeerControllerIT {

    @Autowired TestRestTemplate restTemplate;

    @Test
    void testListBeers() {
        // Given
        // When
        BeerPagedList result = restTemplate.getForObject("/api/v1/beer", BeerPagedList.class);
        // Then
        assertThat(result.getContent()).hasSize(3);
    }
}
