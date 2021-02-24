package uk.co.marvel.character.data.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.co.marvel.character.data.marvel.Thumbnail;

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
