package guru.springframework.brewery.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.brewery.service.BeerService;
import guru.springframework.brewery.web.model.BeerDto;
import guru.springframework.brewery.web.model.BeerPagedList;
import guru.springframework.brewery.web.model.BeerStyleEnum;

@ExtendWith(MockitoExtension.class)
class BeerControllerTest {
    @Mock BeerService beerService;
    @InjectMocks BeerController controller;

    MockMvc mockMvc;
    BeerDto validBeer;

    @BeforeEach
    void setUp() {
        validBeer = BeerDto.builder()
            .id(UUID.randomUUID())
            .version(1)
            .beerName("Beer1")
            .beerStyle(BeerStyleEnum.PALE_ALE)
            .price(new BigDecimal("12.99"))
            .quantityOnHand(4)
            .upc(123456789012L)
            .createdDate(OffsetDateTime.now())
            .lastModifiedDate(OffsetDateTime.now())
            .build();
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getBeerById() throws Exception {
        // Given
        when(beerService.findBeerById(any())).thenReturn(validBeer);
        // When
        mockMvc.perform(get("/api/v1/beer/{beerId}", UUID.randomUUID()))
        // Then
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is(validBeer.getId().toString())))
            .andExpect(jsonPath("$.beerName", is("Beer1")));
    }

    @Nested
    class TestListOperations {
        @Captor ArgumentCaptor<String> beerNameCaptor;
        @Captor ArgumentCaptor<BeerStyleEnum> beerStyleEnumCaptor;
        @Captor ArgumentCaptor<PageRequest> pageRequestCaptor;
        BeerPagedList beerPagedList;

        @BeforeEach
        void setUp() {
            List<BeerDto> beers = List.of(
                validBeer,
                BeerDto.builder()
                    .version(1)
                    .beerName("Beer4")
                    .upc(123123123122L)
                    .beerStyle(BeerStyleEnum.PALE_ALE)
                    .price(new BigDecimal("12.99"))
                    .quantityOnHand(66)
                    .createdDate(OffsetDateTime.now())
                    .lastModifiedDate(OffsetDateTime.now())
                    .build()
            );
            beerPagedList = new BeerPagedList(new PageImpl<>(beers, PageRequest.of(1, 1), 2L));
            when(beerService.listBeers(beerNameCaptor.capture(), beerStyleEnumCaptor.capture(), pageRequestCaptor.capture())).thenReturn(beerPagedList);
        }

        @Test
        void listBeers() throws Exception {
            // Given
            // When
            mockMvc.perform(get("/api/v1/beer")
                    .accept(MediaType.APPLICATION_JSON))
            // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id", is(validBeer.getId().toString())));
        }
    }
}
