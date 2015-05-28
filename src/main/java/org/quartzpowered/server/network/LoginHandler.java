/*
 * This file is a component of Quartz Powered, this license makes sure any work
 * associated with Quartz Powered, must follow the conditions of the license included.
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Quartz Powered
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.quartzpowered.server.network;

import net.engio.mbassy.listener.Handler;
import org.quartzpowered.common.util.CryptoUtil;
import org.quartzpowered.network.session.Session;
import org.quartzpowered.protocol.data.component.TextComponent;
import org.quartzpowered.protocol.packet.common.client.KickPacket;
import org.quartzpowered.protocol.packet.login.client.EncryptionRequestPacket;
import org.quartzpowered.protocol.packet.login.server.EncryptionResponsePacket;
import org.quartzpowered.protocol.packet.login.server.LoginRequestPacket;
import org.quartzpowered.server.Server;
import org.quartzpowered.server.network.auth.AuthRunnableFactory;
import org.slf4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import java.math.BigInteger;
import java.security.*;
import java.util.Arrays;

public class LoginHandler {
    @Inject private Logger logger;
    @Inject private Server server;
    @Inject private CryptoUtil cryptoUtil;
    @Inject private AuthRunnableFactory authRunnableFactory;

    private final byte[] publicKey;
    private final PrivateKey privateKey;

    @Inject
    public LoginHandler(Server server, CryptoUtil cryptoUtil) {
        KeyPair keyPair = server.getKeyPair();
        publicKey = cryptoUtil.toX509(keyPair.getPublic()).getEncoded();
        privateKey = keyPair.getPrivate();
    }

    @Handler
    public void onLoginRequest(LoginRequestPacket packet) {
        Session session = packet.getSender();

        byte[] verifyToken = cryptoUtil.generateToken(4);

        session.setVerifyUsername(packet.getUsername());
        session.setVerifyToken(verifyToken);

        EncryptionRequestPacket response = new EncryptionRequestPacket();

        response.setSessionId(session.getSessionId());
        response.setPublicKey(publicKey);
        response.setVerifyToken(verifyToken);

        session.send(response);
    }

    @Handler
    public void onEncryptionResponse(EncryptionResponsePacket packet) {
        Session session = packet.getSender();

        Cipher cipher;
        try {
            cipher = Cipher.getInstance("RSA");
        } catch (GeneralSecurityException ex) {
            logger.error("Could not initialize RSA cipher", ex);
            session.disconnect(new KickPacket(new TextComponent("Unable to initialize RSA cipher.")));
            return;
        }

        SecretKey sharedSecret;
        try {
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            sharedSecret = new SecretKeySpec(cipher.doFinal(packet.getSharedSecret()), "AES");
        } catch (Exception ex) {
            logger.error("Could not decrypt shared secret", ex);
            session.disconnect(new KickPacket(new TextComponent("Unable to decrypt shared secret.")));
            return;
        }


        byte[] verifyToken;
        try {
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            verifyToken = cipher.doFinal(packet.getVerifyToken());
        } catch (Exception ex) {
            logger.error("Could not decrypt verify token", ex);
            session.disconnect(new KickPacket(new TextComponent("Unable to decrypt verify token.")));
            return;
        }

        if (!Arrays.equals(verifyToken, session.getVerifyToken())) {
            session.disconnect(new KickPacket(new TextComponent("Invalid verify token.")));
            return;
        }

        session.enableEncryption(sharedSecret);

        String hash;
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(session.getSessionId().getBytes());
            digest.update(sharedSecret.getEncoded());
            digest.update(publicKey);

            // BigInteger takes care of sign and leading zeroes
            hash = new BigInteger(digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException ex) {
            logger.error("Unable to generate SHA-1 digest", ex);
            session.disconnect(new KickPacket(new TextComponent("Failed to hash login data.")));
            return;
        }

        Thread clientAuthThread = new Thread(authRunnableFactory.create(session, hash));
        clientAuthThread.setName("ClientAuthThread{" + session.getVerifyUsername() + "}");
        clientAuthThread.start();
    }
}
