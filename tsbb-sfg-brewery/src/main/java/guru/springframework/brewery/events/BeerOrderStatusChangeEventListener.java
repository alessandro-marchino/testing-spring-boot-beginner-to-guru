package guru.springframework.brewery.events;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BeerOrderStatusChangeEventListener {

    @Async
    @EventListener
    public void listen(BeerOrderStatusChangeEvent event) {
        log.info("I got an order status change event: {}", event);
    }
}
