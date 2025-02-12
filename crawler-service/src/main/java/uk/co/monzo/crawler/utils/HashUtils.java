package uk.co.monzo.crawler.utils;

import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {


    private HashUtils() {

    }

    private static final String ENCRYPTION_ALGORITHM = "MD5";

    public static String getHashValue(String input){
        if(StringUtils.isBlank(input)) {
            return StringUtils.EMPTY;
        }
        String hashValue = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ENCRYPTION_ALGORITHM);
            messageDigest.update(input.getBytes());
            byte[] digestedBytes = messageDigest.digest();
            hashValue = DatatypeConverter.printHexBinary(digestedBytes);
        }
        catch(NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
        }
        return hashValue.toLowerCase();
    }
}
