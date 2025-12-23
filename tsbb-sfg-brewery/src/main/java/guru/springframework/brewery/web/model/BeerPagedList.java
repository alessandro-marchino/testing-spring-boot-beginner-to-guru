package guru.springframework.brewery.web.model;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

public class BeerPagedList extends PagedModel<BeerDto> {

    @JsonCreator(mode = Mode.PROPERTIES)
    public BeerPagedList(@JsonProperty("content") List<BeerDto> content,
            @JsonProperty("page") MyPage myPage) {
        super(new PageImpl<>(content, PageRequest.of(myPage.number, myPage.size), myPage.totalElements));
    }

    private static class MyPage {
        final int number;
        final int size;
        final long totalElements;
        @JsonCreator(mode = Mode.PROPERTIES)
        public MyPage(@JsonProperty("number") int number,
                @JsonProperty("size") int size,
                @JsonProperty("totalElements") Long totalElements) {
            this.number = number;
            this.size = size;
            this.totalElements = totalElements;
        }
    }

    public BeerPagedList(Page<BeerDto> content) {
        super(content);
    }

}
