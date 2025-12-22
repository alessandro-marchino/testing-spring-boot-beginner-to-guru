package guru.springframework.brewery.web.mappers;

import org.mapstruct.Mapper;

import guru.springframework.brewery.domain.Beer;
import guru.springframework.brewery.domain.BeerOrder;
import guru.springframework.brewery.domain.BeerOrderLine;
import guru.springframework.brewery.web.model.BeerOrderDto;
import guru.springframework.brewery.web.model.BeerOrderLineDto;

@Mapper
public interface BeerOrderMapper {

    BeerOrderDto beerOrderToDto(BeerOrder beerOrder);

    BeerOrder dtoToBeerOrder(BeerOrderDto dto);

    BeerOrderLineDto beerOrderLineToDto(BeerOrderLine line);

    default BeerOrderLine dtoToBeerOrder(BeerOrderLineDto dto){
        return BeerOrderLine.builder()
            .orderQuantity(dto.getOrderQuantity())
            .beer(Beer.builder().id(dto.getBeerId()).build())
            .build();
    }
}