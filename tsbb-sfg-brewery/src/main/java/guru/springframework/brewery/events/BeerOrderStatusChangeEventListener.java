package guru.springframework.brewery.events;

import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import guru.springframework.brewery.web.model.OrderStatusEnum;
import guru.springframework.brewery.web.model.OrderStatusUpdate;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BeerOrderStatusChangeEventListener {

    final RestTemplate restTemplate;

    public BeerOrderStatusChangeEventListener(RestTemplateBuilder builder) {
        restTemplate = builder.build();
    }

    @Async
    @EventListener
    public void listen(BeerOrderStatusChangeEvent event) {
        log.info("I got an order status change event: {}", event);
        OrderStatusUpdate update = OrderStatusUpdate.builder()
            .id(event.getBeerOrder().getId())
            .orderId(event.getBeerOrder().getId())
            .version(event.getBeerOrder().getVersion() != null ? event.getBeerOrder().getVersion().intValue() : null)
            .createdDate(event.getBeerOrder().getCreatedDate())
            .lastModifiedDate(event.getBeerOrder().getLastModifiedDate())
            .orderStatus(OrderStatusEnum.valueOf(event.getBeerOrder().getOrderStatus().toString()))
            .build();
        update.setCustomerRef(event.getBeerOrder().getCustomerRef());
        try {
            log.debug("Posting to callback URL");
            restTemplate.postForObject(event.getBeerOrder().getOrderStatusCallbackUrl(), update, String.class);
        } catch(Throwable t) {
            log.error("Error performing callback for order: {}", event.getBeerOrder().getId(), t);
        }
    }
}
