package guru.springframework.brewery.repositories;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

import guru.springframework.brewery.domain.BeerOrderLine;

public interface BeerOrderLineRepository extends PagingAndSortingRepository<BeerOrderLine, UUID> {
    // Empty
}
