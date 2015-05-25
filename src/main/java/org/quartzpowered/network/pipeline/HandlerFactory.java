package org.quartzpowered.network.pipeline;

import javax.crypto.SecretKey;

public interface HandlerFactory {
    public EncryptionHandler createEncryptionHandler(SecretKey sharedSecret);
}
