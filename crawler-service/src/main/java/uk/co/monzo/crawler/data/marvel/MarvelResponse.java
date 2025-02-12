package uk.co.monzo.crawler.data.marvel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarvelResponse {

    private Integer offset;
    private Integer limit;
    private Integer total;
    private Integer count;
    private List<Object> results;
}
