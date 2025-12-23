package guru.springframework.brewery.web.model;

import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;

public class BeerOrderPagedList extends PagedModel<BeerOrderDto> {

    public BeerOrderPagedList(Page<BeerOrderDto> content) {
        super(content);
    }
}