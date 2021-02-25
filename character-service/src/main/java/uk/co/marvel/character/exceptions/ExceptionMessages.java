package uk.co.marvel.character.exceptions;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessages {

    public static final String EXTERNAL_SERVICE_EXCEPTION_MESSAGE = "Cannot reach external upstream service: [%s]";
    public static final String CHARACTER_EXCEPTION_MESSAGE = "Failed to retrieve Character data with message: [%s]";
    public static final String MARVEL_ERROR = "Failed to retrieve data from Marvel";
    public static final String UNMARSHAL_ERROR = "Error unmarshalling JSON";
    public static final String CHARACTER_ERROR = "Could not find Character!";
}
