package guru.springframework.brewery.service;

import java.util.UUID;

import org.springframework.data.domain.Pageable;

import guru.springframework.brewery.web.model.BeerOrderDto;
import guru.springframework.brewery.web.model.BeerOrderPagedList;

public interface BeerOrderService {
    BeerOrderPagedList listOrders(UUID customerId, Pageable pageable);

    BeerOrderDto placeOrder(UUID customerId, BeerOrderDto beerOrderDto);

    BeerOrderDto getOrderById(UUID customerId, UUID orderId);

    void pickupOrder(UUID customerId, UUID orderId);
}