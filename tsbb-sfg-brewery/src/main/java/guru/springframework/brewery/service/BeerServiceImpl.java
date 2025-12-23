package guru.springframework.brewery.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import guru.springframework.brewery.domain.Beer;
import guru.springframework.brewery.repositories.BeerRepository;
import guru.springframework.brewery.web.mappers.BeerMapper;
import guru.springframework.brewery.web.model.BeerDto;
import guru.springframework.brewery.web.model.BeerPagedList;
import guru.springframework.brewery.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest) {

        Page<Beer> beerPage;

        if (!isEmpty(beerName) && !isEmpty(beerStyle)) {
            // search both
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (!isEmpty(beerName) && isEmpty(beerStyle)) {
            // search beer name
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (isEmpty(beerName) && !isEmpty(beerStyle)) {
            // search beer style
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }
        return new BeerPagedList(beerPage.map(beerMapper::beerToBeerDto));
    }

    @Override
    public BeerDto findBeerById(UUID beerId) {
        return beerRepository.findById(beerId)
            .map(beerMapper::beerToBeerDto)
            .orElseThrow(() -> new RuntimeException("Not Found"));
    }

    private boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private boolean isEmpty(Object str) {
        return str == null;
    }
}