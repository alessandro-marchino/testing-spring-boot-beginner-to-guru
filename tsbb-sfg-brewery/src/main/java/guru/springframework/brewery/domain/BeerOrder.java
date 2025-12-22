package guru.springframework.brewery.domain;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class BeerOrder extends BaseEntity {

    @Builder
    public BeerOrder(UUID id, Long version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate, String customerRef, Customer customer, Set<BeerOrderLine> beerOrderLines, OrderStatusEnum orderStatus, String orderStatusCallbackUrl) {
        super(id, version, createdDate, lastModifiedDate);
        this.customerRef = customerRef;
        this.customer = customer;
        this.beerOrderLines = beerOrderLines;
        this.orderStatus = orderStatus;
        this.orderStatusCallbackUrl = orderStatusCallbackUrl;
    }

    private String customerRef;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "beerOrder", cascade = CascadeType.PERSIST)
    @Fetch(FetchMode.JOIN)
    private Set<BeerOrderLine> beerOrderLines;

    private OrderStatusEnum orderStatus = OrderStatusEnum.NEW;
    private String orderStatusCallbackUrl;
}