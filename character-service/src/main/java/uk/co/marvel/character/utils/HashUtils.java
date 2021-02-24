package uk.co.marvel.character.utils;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {


    private HashUtils() {

    }

    public static String getHashValue(byte[] input, String algorithm){
        String hashValue = "";

        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(input);
            byte[] digestedBytes = messageDigest.digest();
            hashValue = DatatypeConverter.printHexBinary(digestedBytes);
        }
        catch(NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
        }
        return hashValue.toLowerCase();
    }
}
