package guru.springframework.brewery.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.brewery.domain.BeerOrder;
import guru.springframework.brewery.domain.Customer;
import guru.springframework.brewery.domain.OrderStatusEnum;

public interface BeerOrderRepository extends JpaRepository<BeerOrder, UUID> {

    Page<BeerOrder> findAllByCustomer(Customer customer, Pageable pageable);
    List<BeerOrder> findAllByOrderStatus(OrderStatusEnum orderStatusEnum);
}
