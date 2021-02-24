package uk.co.marvel.character.utils;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class HashUtilsTest {

    @Test
    public void shouldTestHash() {
        var result = HashUtils.getHashValue("176d134ca4056b703fc973192cd5bc2fd82f74e7043631599fd7eaed301e5fc03b9ffc013".getBytes(), "MD5").toLowerCase();
        System.out.println(result);
    }
}
