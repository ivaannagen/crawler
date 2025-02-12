package uk.co.monzo.crawler.data.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.monzo.crawler.data.marvel.Thumbnail;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Character {

    private Integer id;

    private String name;

    private String description;

    private Thumbnail thumbnail;

}
