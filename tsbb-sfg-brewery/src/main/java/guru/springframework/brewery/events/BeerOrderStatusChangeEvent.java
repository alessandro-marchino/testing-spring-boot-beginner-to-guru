package guru.springframework.brewery.events;

import org.springframework.context.ApplicationEvent;

import guru.springframework.brewery.domain.BeerOrder;
import guru.springframework.brewery.domain.OrderStatusEnum;
import lombok.Getter;

public class BeerOrderStatusChangeEvent extends ApplicationEvent {

    @Getter
    private final OrderStatusEnum previousState;

    public BeerOrderStatusChangeEvent(BeerOrder source, OrderStatusEnum previousStatus) {
        super(source);
        this.previousState = previousStatus;
    }

}
