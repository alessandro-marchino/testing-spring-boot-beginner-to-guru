package guru.springframework.brewery.web.model;

import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;

public class BeerPagedList extends PagedModel<BeerDto> {

    public BeerPagedList(Page<BeerDto> content) {
        super(content);
    }

}