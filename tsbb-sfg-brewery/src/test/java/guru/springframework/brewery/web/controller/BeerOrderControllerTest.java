package guru.springframework.brewery.web.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
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
import org.springframework.boot.restdocs.test.autoconfigure.AutoConfigureRestDocs;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import guru.springframework.brewery.domain.OrderStatusEnum;
import guru.springframework.brewery.service.BeerOrderService;
import guru.springframework.brewery.web.model.BeerOrderDto;
import guru.springframework.brewery.web.model.BeerOrderPagedList;
import tools.jackson.databind.json.JsonMapper;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(BeerOrderController.class)
@AutoConfigureRestDocs
public class BeerOrderControllerTest {
    @MockitoBean BeerOrderService service;
    @Autowired MockMvc mockMvc;
    UUID customerId;

    @BeforeEach
    void setUp() {
        customerId = UUID.randomUUID();
    }
    
    @AfterEach
    void tearDown() {
        reset(service);
    }
    
    @Test
    void getOrder() throws Exception {
        // Given
        UUID orderId = UUID.randomUUID();
        BeerOrderDto order = new BeerOrderDto();
        order.setId(orderId);
        when(service.getOrderById(eq(customerId), eq(orderId))).thenReturn(order);
        // When
        mockMvc.perform(get("/api/v1/customers/{customerId}/orders/{orderId}", customerId, orderId))
        // Then
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is(orderId.toString())))
            .andDo(document("GET v1/customers/_customerId/orders/_orderId",
                pathParameters(
                    parameterWithName("customerId").description("UUID of customer"),
                    parameterWithName("orderId").description("UUID of the order")
                ),
                responseFields(
                    fieldWithPath("id").description("Id of the order"),
                    fieldWithPath("version").description("Version number"),
                    fieldWithPath("createdDate").description("Creation date"),
                    fieldWithPath("lastModifiedDate").description("Update date"),
                    fieldWithPath("customerId").description("Id of the customer"),
                    fieldWithPath("beerOrderLines").description("Beer order lines"),
                    fieldWithPath("orderStatus").description("Order status"),
                    fieldWithPath("orderStatusCallbackUrl").description("ORder callback URL"),
                    fieldWithPath("customerRef").description("customer reference")
                )
            ));
    }

    @Nested
    @ExtendWith(MockitoExtension.class)
    class BeerOrderControllerListOrdersTest {
        @Captor ArgumentCaptor<Pageable> pageableCaptor;

        @Test
        void listOrders() throws Exception {
            // Given
            BeerOrderDto order1 = new BeerOrderDto(UUID.randomUUID(), 1, OffsetDateTime.now(), OffsetDateTime.now(), customerId, List.of(), OrderStatusEnum.NEW, "", "");
            BeerOrderDto order2 = new BeerOrderDto(UUID.randomUUID(), 1, OffsetDateTime.now(), OffsetDateTime.now(), customerId, List.of(), OrderStatusEnum.NEW, "", "");
            BeerOrderPagedList res = new BeerOrderPagedList(new PageImpl<>(List.of( order1, order2 )));
            when(service.listOrders(eq(customerId), pageableCaptor.capture())).thenReturn(res);
            // When
            mockMvc.perform(get("/api/v1/customers/{customerId}/orders", customerId)
                    .param("pageNumber", "1")
                    .param("pageSize", "1"))
            // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id", is(order1.getId().toString())))
                .andExpect(jsonPath("$.content[0].version", is(1)))
                .andDo(document("GET v1/customers/_customerId/orders",
                    pathParameters(
                        parameterWithName("customerId").description("UUID of customer")
                    ),
                    queryParameters(
                        parameterWithName("pageNumber").optional().description("Page number"),
                        parameterWithName("pageSize").optional().description("Page size")
                    ),
                    responseFields(
                        fieldWithPath("content[].id").description("Id of the order"),
                        fieldWithPath("content[].version").description("Version number"),
                        fieldWithPath("content[].createdDate").description("Creation date"),
                        fieldWithPath("content[].lastModifiedDate").description("Update date"),
                        fieldWithPath("content[].customerId").description("Id of the customer"),
                        fieldWithPath("content[].beerOrderLines").description("Beer order lines"),
                        fieldWithPath("content[].orderStatus").description("Order status"),
                        fieldWithPath("content[].orderStatusCallbackUrl").description("ORder callback URL"),
                        fieldWithPath("content[].customerRef").description("customer reference"),

                        fieldWithPath("page.size").description("Size of the page"),
                        fieldWithPath("page.number").description("Number of the page"),
                        fieldWithPath("page.totalElements").description("Total elements"),
                        fieldWithPath("page.totalPages").description("Total pages")
                    )
                ));
            assertThat(pageableCaptor.getValue().getPageNumber()).isEqualTo(1);
            assertThat(pageableCaptor.getValue().getPageSize()).isEqualTo(1);
        }
        @Test
        void listOrdersEmpty() throws Exception {
            // Given
            BeerOrderPagedList res = new BeerOrderPagedList(Page.empty());
            when(service.listOrders(eq(customerId), pageableCaptor.capture())).thenReturn(res);
            // When
            mockMvc.perform(get("/api/v1/customers/{customerId}/orders", customerId)
                    .param("pageNumber", "1")
                    .param("pageSize", "1"))
            // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(0)));
            assertThat(pageableCaptor.getValue().getPageNumber()).isEqualTo(1);
            assertThat(pageableCaptor.getValue().getPageSize()).isEqualTo(1);
        }
        @Test
        void listOrdersUSeDefaults() throws Exception {
            // Given
            BeerOrderPagedList res = new BeerOrderPagedList(Page.empty());
            when(service.listOrders(eq(customerId), pageableCaptor.capture())).thenReturn(res);
            // When
            mockMvc.perform(get("/api/v1/customers/{customerId}/orders", customerId))
            // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(0)));
            assertThat(pageableCaptor.getValue().getPageNumber()).isEqualTo(0);
            assertThat(pageableCaptor.getValue().getPageSize()).isEqualTo(25);
        }
    }


    @Test
    void pickupOrder() throws Exception {
        // Given
        UUID orderId = UUID.randomUUID();
        // When
        mockMvc.perform(put("/api/v1/customers/{customerId}/orders/{orderId}/pickup", customerId, orderId))
        // Then
            .andExpect(status().isNoContent())
            .andDo(document("PUT v1/customers/_customerId/orders/_orderId/pickup",
                pathParameters(
                    parameterWithName("customerId").description("UUID of customer"),
                    parameterWithName("orderId").description("UUID of order")
                )
            ));
    }

    @Test
    void placeOrder() throws Exception {
        // Given
        BeerOrderDto order = new BeerOrderDto(UUID.randomUUID(), 1, OffsetDateTime.now(), OffsetDateTime.now(), customerId, List.of(), OrderStatusEnum.NEW, "", "");
        when(service.placeOrder(eq(customerId), any(BeerOrderDto.class))).thenReturn(order);
        // When
        mockMvc.perform(post("/api/v1/customers/{customerId}/orders", customerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(Map.of(
                    "version", order.getVersion(),
                    "customerId", customerId,
                    "orderStatus", order.getOrderStatus()
                ))))
        // Then
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is(order.getId().toString())))
            .andExpect(jsonPath("$.version", is(order.getVersion())))
            .andDo(document("POST v1/customers/_customerId/orders",
                pathParameters(
                    parameterWithName("customerId").description("UUID of customer")
                ),
                requestFields(
                    fieldWithPath("version").description("Version number"),
                    fieldWithPath("customerId").description("UUID of customer"),
                    fieldWithPath("orderStatus").description("Order status")
                ),
                responseFields(
                    fieldWithPath("id").description("Id of the order"),
                    fieldWithPath("version").description("Version number"),
                    fieldWithPath("createdDate").description("Creation date"),
                    fieldWithPath("lastModifiedDate").description("Update date"),
                    fieldWithPath("customerId").description("Id of the customer"),
                    fieldWithPath("beerOrderLines").description("Beer order lines"),
                    fieldWithPath("orderStatus").description("Order status"),
                    fieldWithPath("orderStatusCallbackUrl").description("ORder callback URL"),
                    fieldWithPath("customerRef").description("customer reference")
                )
            ));
    }

    private String toJson(Object obj) {
        return JsonMapper.shared().writeValueAsString(obj);
    }

}
