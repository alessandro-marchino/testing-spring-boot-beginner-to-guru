package guru.springframework.brewery.service;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;

import guru.springframework.brewery.web.model.BeerDto;
import guru.springframework.brewery.web.model.BeerPagedList;
import guru.springframework.brewery.web.model.BeerStyleEnum;

public interface BeerService {
    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest);

    BeerDto findBeerById(UUID beerId);
}
