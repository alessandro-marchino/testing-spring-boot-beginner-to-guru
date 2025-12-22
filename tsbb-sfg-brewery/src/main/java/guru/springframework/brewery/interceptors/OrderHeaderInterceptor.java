package guru.springframework.brewery.interceptors;

import org.hibernate.Interceptor;
import org.hibernate.type.Type;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import guru.springframework.brewery.domain.BeerOrder;
import guru.springframework.brewery.domain.OrderStatusEnum;
import guru.springframework.brewery.events.BeerOrderStatusChangeEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderHeaderInterceptor implements Interceptor {

    private final ApplicationEventPublisher publisher;

    @Override
    public boolean onFlushDirty(Object entity, Object id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        if(entity instanceof BeerOrder bo) {
            for(Object curObj : currentState) {
                if(curObj instanceof OrderStatusEnum curStatus) {
                    for(Object prevObj : previousState) {
                        if(prevObj instanceof OrderStatusEnum prevStatus) {
                            if(curStatus != prevStatus) {
                                log.debug("Order status change detected");
                                publisher.publishEvent(new BeerOrderStatusChangeEvent(bo, prevStatus));
                            }
                        }
                    }
                }
            }
        }

        return Interceptor.super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    }
}
