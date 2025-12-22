package guru.springframework.brewery.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.brewery.domain.Beer;
import guru.springframework.brewery.domain.BeerInventory;

public interface BeerInventoryRepository extends JpaRepository<BeerInventory, UUID> {

    List<BeerInventory> findAllByBeer(Beer beer);
}
