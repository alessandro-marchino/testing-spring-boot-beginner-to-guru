package guru.springframework.brewery.events;

import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.restclient.RestTemplateBuilder;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;

import guru.springframework.brewery.domain.BeerOrder;
import guru.springframework.brewery.domain.OrderStatusEnum;

@WireMockTest
public class BeerOrderStatusChangeEventListenerTest {

    BeerOrderStatusChangeEventListener listener;

    @BeforeEach
    void setUp() {
        RestTemplateBuilder builder = new RestTemplateBuilder();
        listener = new BeerOrderStatusChangeEventListener(builder);
    }
    @Test
    void listen(WireMockRuntimeInfo wmRuntimeInfo) {
        stubFor(post("/update").willReturn(ok()));

        BeerOrder beerOrder = BeerOrder.builder()
            .orderStatus(OrderStatusEnum.READY)
            .orderStatusCallbackUrl(wmRuntimeInfo.getHttpBaseUrl() + "/update")
            .build();
        BeerOrderStatusChangeEvent event = new BeerOrderStatusChangeEvent(beerOrder, OrderStatusEnum.NEW);
        listener.listen(event);

        verify(1, postRequestedFor(urlEqualTo("/update")));
    }
}
