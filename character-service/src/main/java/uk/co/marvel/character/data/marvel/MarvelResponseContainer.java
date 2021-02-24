package uk.co.marvel.character.data.marvel;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarvelResponseContainer implements Serializable {

    private MarvelResponse data;
}
