package guru.springframework.brewery.web.model;

import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;

public class CreditCardPagedList extends PagedModel<CreditCardDto> {

    public CreditCardPagedList(Page<CreditCardDto> content) {
        super(content);
    }
}