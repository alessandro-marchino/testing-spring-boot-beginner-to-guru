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
public class BeerInventory extends BaseEntity {

    @Builder
    public BeerInventory(UUID id, Long version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate, Beer beer, Integer quantityOnHand) {
        super(id, version, createdDate, lastModifiedDate);
        this.beer = beer;
        this.quantityOnHand = quantityOnHand;
    }

    @ManyToOne
    private Beer beer;

    private Integer quantityOnHand = 0;
}
