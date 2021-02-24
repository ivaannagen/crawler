package uk.co.marvel.character.data.marvel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Thumbnail {

    private String path;

    private String extension;
}
