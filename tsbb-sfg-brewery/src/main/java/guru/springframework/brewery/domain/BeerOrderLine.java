package guru.springframework.brewery.domain;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class BeerOrderLine extends BaseEntity {

    @Builder
    public BeerOrderLine(UUID id, Long version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate, BeerOrder beerOrder, Beer beer, Integer orderQuantity, Integer quantityAllocated) {
        super(id, version, createdDate, lastModifiedDate);
        this.beerOrder = beerOrder;
        this.beer = beer;
        this.orderQuantity = orderQuantity;
        this.quantityAllocated = quantityAllocated;
    }

    @ManyToOne
    private BeerOrder beerOrder;

    @ManyToOne
    private Beer beer;

    private Integer orderQuantity = 0;
    private Integer quantityAllocated = 0;
}