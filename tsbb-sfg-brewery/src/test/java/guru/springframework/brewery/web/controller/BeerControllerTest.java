package guru.springframework.brewery.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import guru.springframework.brewery.service.BeerService;
import guru.springframework.brewery.web.model.BeerDto;
import guru.springframework.brewery.web.model.BeerPagedList;
import guru.springframework.brewery.web.model.BeerStyleEnum;

@WebMvcTest(BeerController.class)
class BeerControllerTest {
    @MockitoBean BeerService beerService;

    @Autowired
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
    }

    @AfterEach
    void tearDown() {
        reset(beerService);
    }

    @Test
    void getBeerById() throws Exception {
        // Given
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
        when(beerService.findBeerById(any())).thenReturn(validBeer);
        // When
        MvcResult result = mockMvc.perform(get("/api/v1/beer/{beerId}", UUID.randomUUID()))
        // Then
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is(validBeer.getId().toString())))
            .andExpect(jsonPath("$.beerName", is("Beer1")))
            .andExpect(jsonPath("$.createdDate", is(dateTimeFormatter.format(validBeer.getCreatedDate()))))
            .andReturn();
        System.out.println(result.getResponse().getContentAsString());

    }

    @Nested
    @ExtendWith(MockitoExtension.class)
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
            MvcResult result = mockMvc.perform(get("/api/v1/beer")
                    .accept(MediaType.APPLICATION_JSON))
            // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id", is(validBeer.getId().toString())))
                .andReturn();
            System.out.println(result.getResponse().getContentAsString());
        }
    }

}
