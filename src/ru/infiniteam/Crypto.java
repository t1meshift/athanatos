package ru.infiniteam;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoException;

import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;

import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.jcajce.provider.digest.SHA3;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by t1meshft on 09.04.2017.
 */
public class Crypto {
    public static class AES {
        private static BlockCipher engine = new AESEngine();
        private static BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(engine));

        static byte[] symmetricCrypto( boolean encrypt, BufferedBlockCipher cipher, CipherParameters params, byte[] input) throws CryptoException {
            cipher.init( encrypt, params );
            byte[] output = new byte[cipher.getOutputSize(input.length)];

            int outputLen = cipher.processBytes(input, 0, input.length, output, 0);
            cipher.doFinal(output, outputLen);

            return output;
        }

        static byte[] encrypt(CipherParameters params, byte[] input) throws CryptoException {
            return symmetricCrypto( true, cipher, params, input );
        }

        static byte[] decrypt(CipherParameters params, byte[] input) throws CryptoException {
            return symmetricCrypto( false, cipher, params, input );
        }
    }

    public static String generatePassword(int numChars) {
        final char[] VALID_CHARACTERS =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456879!_-=".toCharArray();
        SecureRandom srand = new SecureRandom();
        Random rand = new Random();
        char[] buff = new char[numChars];

        for (int i = 0; i < numChars; ++i) {
            // reseed rand once you've used up all available entropy bits
            if ((i % 10) == 0) {
                rand.setSeed(srand.nextLong()); // 64 bits of random!
            }
            buff[i] = VALID_CHARACTERS[rand.nextInt(VALID_CHARACTERS.length)];
        }
        return new String(buff);
    }

    public static class SHA3 {
        //????????
    }
}


