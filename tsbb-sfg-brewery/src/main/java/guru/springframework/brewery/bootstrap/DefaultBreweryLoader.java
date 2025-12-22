package guru.springframework.brewery.bootstrap;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.brewery.domain.Beer;
import guru.springframework.brewery.domain.BeerInventory;
import guru.springframework.brewery.domain.BeerOrder;
import guru.springframework.brewery.domain.BeerOrderLine;
import guru.springframework.brewery.domain.Brewery;
import guru.springframework.brewery.domain.BreweryRepository;
import guru.springframework.brewery.domain.Customer;
import guru.springframework.brewery.domain.CustomerRepository;
import guru.springframework.brewery.domain.OrderStatusEnum;
import guru.springframework.brewery.repositories.BeerInventoryRepository;
import guru.springframework.brewery.repositories.BeerOrderRepository;
import guru.springframework.brewery.repositories.BeerRepository;
import guru.springframework.brewery.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DefaultBreweryLoader implements CommandLineRunner {

    private final BreweryRepository breweryRepository;
    private final BeerRepository beerRepository;
    private final BeerInventoryRepository beerInventoryRepository;
    private final BeerOrderRepository beerOrderRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadBreweryData();
    }

    private void loadBreweryData() {
        if (breweryRepository.count() == 0){
            breweryRepository.save(Brewery
                    .builder()
                    .breweryName("Cage Brewing")
                    .build());

            Beer mangoBobs = Beer.builder()
                    .beerName("Mango Bobs")
                    .beerStyle(BeerStyleEnum.IPA)
                    .minOnHand(12)
                    .quantityToBrew(200)
                    .upc(337010000001L)
                    .build();

            beerRepository.save(mangoBobs);

            beerInventoryRepository.save(BeerInventory.builder()
                    .beer(mangoBobs)
                    .quantityOnHand(100)
                    .build());

            Beer galaxyCat = Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle(BeerStyleEnum.PALE_ALE)
                    .minOnHand(12)
                    .quantityToBrew(200)
                    .upc(337010000002L)
                    .build();

            beerRepository.save(galaxyCat);

            beerInventoryRepository.save(BeerInventory.builder()
                    .beer(galaxyCat)
                    .quantityOnHand(100)
                    .build());

            Beer pinball = Beer.builder()
                    .beerName("Pinball Porter")
                    .beerStyle(BeerStyleEnum.PORTER)
                    .minOnHand(12)
                    .quantityToBrew(200)
                    .upc(337010000003L)
                    .build();

            beerRepository.save(pinball);

            beerInventoryRepository.save(BeerInventory.builder()
                    .beer(pinball)
                    .quantityOnHand(100)
                    .build());

            Customer testCustomer = customerRepository.save(Customer
                    .builder()
                    .customerName("Test 1").apiKey(UUID.randomUUID())
                    .build());

            Set<BeerOrderLine> orderLines1 = new HashSet<>();
            orderLines1.add(BeerOrderLine.builder().beer(galaxyCat).orderQuantity(15).quantityAllocated(0).build());
            orderLines1.add(BeerOrderLine.builder().beer(pinball).orderQuantity(7).quantityAllocated(0).build());

            BeerOrder testOrder1 = beerOrderRepository.save(BeerOrder.builder()
                    .orderStatus(OrderStatusEnum.NEW)
                    .customer(testCustomer)
                    .customerRef("testOrder1")
                    .orderStatusCallbackUrl("http://example.com/post")
                    .beerOrderLines(orderLines1)
                    .build());

            orderLines1.forEach(line -> line.setBeerOrder(testOrder1));

            beerOrderRepository.save(testOrder1);
        }
    }
}