package guru.springframework.brewery.domain;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

import guru.springframework.brewery.web.model.BeerStyleEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Beer extends BaseEntity {

    @Builder
    public Beer(UUID id, Long version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate, String beerName, BeerStyleEnum beerStyle, Long upc, Integer minOnHand, Integer quantityToBrew, BigDecimal price, Set<BeerInventory> beerInventory) {
        super(id, version, createdDate, lastModifiedDate);
        this.beerName = beerName;
        this.beerStyle = beerStyle;
        this.upc = upc;
        this.minOnHand = minOnHand;
        this.quantityToBrew = quantityToBrew;
        this.price = price;
        this.beerInventory = beerInventory;
    }

    private String beerName;
    private BeerStyleEnum beerStyle;

    @Column(unique = true)
    private Long upc;

    /**
     * Min on hand qty - used to trigger brew
     */
    private Integer minOnHand;
    private Integer quantityToBrew;
    private BigDecimal price;

    @OneToMany(mappedBy = "beer")
    private Set<BeerInventory> beerInventory;
}
