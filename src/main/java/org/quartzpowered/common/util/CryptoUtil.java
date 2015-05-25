package org.quartzpowered.common.util;

import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;

@Singleton
public class CryptoUtil {
    @Inject private Logger logger;

    private final SecureRandom random = new SecureRandom();

    public KeyPair generateRSAKeyPair() {
        KeyPair keyPair = null;
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(1024);

            keyPair = generator.generateKeyPair();
        } catch (NoSuchAlgorithmException ex) {
            logger.error("Unable to generate RSA key pair", ex);
        }
        return keyPair;
    }

    public byte[] generateToken(int length) {
        byte[] token = new byte[length];
        random.nextBytes(token);
        return token;
    }

    public Key toX509(Key base) {
        Key key = null;
        try {
            X509EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(base.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            key = keyFactory.generatePublic(encodedKeySpec);
        } catch (Exception ex) {
            logger.error("Unable to generate X509 encoded key", ex);
        }
        return key;
    }
}
