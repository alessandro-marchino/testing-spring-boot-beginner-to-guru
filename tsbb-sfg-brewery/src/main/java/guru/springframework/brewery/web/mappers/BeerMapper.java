package guru.springframework.brewery.web.mappers;

import org.mapstruct.Mapper;

import guru.springframework.brewery.domain.Beer;
import guru.springframework.brewery.web.model.BeerDto;

@Mapper
public interface BeerMapper {

    BeerDto beerToBeerDto(Beer beer);

    Beer beerDtoToBeer(BeerDto beerDto);
}