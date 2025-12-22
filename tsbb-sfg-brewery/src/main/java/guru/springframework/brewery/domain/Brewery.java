package guru.springframework.brewery.domain;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Brewery extends BaseEntity {

    @Builder
    public Brewery(UUID id, Long version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate, String breweryName) {
        super(id, version, createdDate, lastModifiedDate);
        this.breweryName = breweryName;
    }

    private String breweryName;

}