package uk.co.marvel.character.utils;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class HashUtilsTest {

    @Test
    public void shouldTestHashResultIsCorrect() {
        var result = HashUtils.getHashValue("secret-key").toLowerCase();
        assertEquals("33b9b3de94a42d19f47df7021954eaa8", result);
    }

    @Test
    public void shouldTestHashResultIsEmptyWhenInputIsMissing() {
        assertEquals(StringUtils.EMPTY, HashUtils.getHashValue("").toLowerCase());
    }
}
