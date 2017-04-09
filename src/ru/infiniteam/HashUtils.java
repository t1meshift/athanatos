package ru.infiniteam;

/**
 * Created by t1meshft on 09.04.2017.
 */
import com.google.common.base.Charsets;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SHA3Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;

import java.util.Base64;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

public class HashUtils {

    public static Function<String, byte[]> SHA_256 = new Function<String, byte[]>() {
        @Override
        public byte[] apply(String input) {
            return calculateHash(input, new SHA256Digest());
        }
    };

    public static Function<String, byte[]> SHA_512 = new Function<String, byte[]>() {
        @Override
        public byte[] apply(String input) {
            return calculateHash(input, new SHA512Digest());
        }
    };

    public static Function<String, byte[]> SHA3_256 = new Function<String, byte[]>() {
        @Override
        public byte[] apply(String input) {
            return calculateHash(input, new SHA3Digest(256));
        }
    };

    public static Function<String, byte[]> SHA3_512 = new Function<String, byte[]>() {
        @Override
        public byte[] apply(String input) {
            return calculateHash(input, new SHA3Digest(512));
        }
    };

    private static byte[] calculateHash(String input, Digest digest) {
        byte[] inputAsBytes = input.getBytes(Charsets.UTF_8);
        byte[] retValue = new byte[digest.getDigestSize()];
        digest.update(inputAsBytes, 0, inputAsBytes.length);
        digest.doFinal(retValue, 0);
        return retValue;
    }

    private HashUtils() {}

    public static String hash(String input, Function<String, byte[]> hashFunction) {
        checkArgument(!isNullOrEmpty(input), "input can not be empty or null");
        return Base64.getEncoder().encodeToString(hashFunction.apply(input));
    }
    public static String hash(byte[] input, Function<String, byte[]> hashFunction) {
        return Base64.getEncoder().encodeToString(hashFunction.apply(new String(input)));
    }
}