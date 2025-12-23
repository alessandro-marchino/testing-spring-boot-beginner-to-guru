package guru.springframework.brewery.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import guru.springframework.brewery.domain.Beer;
import guru.springframework.brewery.domain.BeerOrder;
import guru.springframework.brewery.domain.Customer;
import guru.springframework.brewery.domain.CustomerRepository;
import guru.springframework.brewery.domain.OrderStatusEnum;
import guru.springframework.brewery.repositories.BeerOrderRepository;
import guru.springframework.brewery.repositories.BeerRepository;
import guru.springframework.brewery.web.mappers.BeerOrderMapper;
import guru.springframework.brewery.web.model.BeerOrderDto;
import guru.springframework.brewery.web.model.BeerOrderPagedList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BeerOrderServiceImpl implements BeerOrderService {

    private final BeerOrderRepository beerOrderRepository;
    private final CustomerRepository customerRepository;
    private final BeerRepository beerRepository;
    private final BeerOrderMapper beerOrderMapper;

    @Override
    public BeerOrderPagedList listOrders(UUID customerId, Pageable pageable) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if(customerOptional.isEmpty()) {
            return null;
        }

        Page<BeerOrder> beerOrderPage = beerOrderRepository.findAllByCustomer(customerOptional.get(), pageable);

        return new BeerOrderPagedList(beerOrderPage.map(beerOrderMapper::beerOrderToDto));
    }

    @Override
    public BeerOrderDto placeOrder(UUID customerId, BeerOrderDto beerOrderDto) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer Not Found"));

        BeerOrder beerOrder = beerOrderMapper.dtoToBeerOrder(beerOrderDto);
        // should not be set by outside client
        beerOrder.setId(null);
        beerOrder.setCustomer(customer);
        beerOrder.setOrderStatus(OrderStatusEnum.NEW);

        //update beers from db, go boom if not found
        beerOrder.getBeerOrderLines().forEach(beerOrderLine -> {
            Beer beer = beerRepository.findById(beerOrderLine.getBeer().getId()).orElseThrow(() -> new RuntimeException("Beer ID not found: " + beerOrderLine.getBeer().getId()));
            beerOrderLine.setBeer(beer);
        });

        BeerOrder savedBeerOrder = beerOrderRepository.save(beerOrder);
        log.debug("Saved Beer Order: {}", beerOrder.getId());

        return beerOrderMapper.beerOrderToDto(savedBeerOrder);
    }

    @Override
    public BeerOrderDto getOrderById(UUID customerId, UUID orderId) {
        return beerOrderMapper.beerOrderToDto(getOrder(customerId, orderId));
    }

    @Override
    public void pickupOrder(UUID customerId, UUID orderId) {
        BeerOrder beerOrder = getOrder(customerId, orderId);

        beerOrder.setOrderStatus(OrderStatusEnum.PICKED_UP);

        beerOrderRepository.save(beerOrder);
    }

    private BeerOrder getOrder(UUID customerId, UUID orderId){
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer Not Found"));
        BeerOrder beerOrder = beerOrderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Beer Order Not Found"));

        // fall to exception if customer id's do not match - order not for customer
        if(beerOrder.getCustomer().getId().equals(customer.getId())){
            return beerOrder;
        }
        throw new RuntimeException("Beer Order Not Found");
    }
}