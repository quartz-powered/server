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
package org.quartzpowered.network.session;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.name.Named;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.quartzpowered.engine.observe.Observer;
import org.quartzpowered.network.pipeline.CompressionHandler;
import org.quartzpowered.network.pipeline.HandlerFactory;
import org.quartzpowered.network.pipeline.NoopHandler;
import org.quartzpowered.network.protocol.Protocol;
import org.quartzpowered.network.protocol.ProtocolState;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.network.session.attribute.AttributeRegistry;
import org.quartzpowered.network.session.attribute.AttributeStorage;
import org.quartzpowered.network.session.profile.PlayerProfile;

import javax.crypto.SecretKey;
import javax.inject.Inject;
import java.util.Random;

import static org.quartzpowered.network.protocol.ProtocolState.HANDSHAKE;

@ToString(of = {"channel", "profile"})
public class Session implements Observer {
    @Inject private HandlerFactory handlerFactory;
    @Inject private NoopHandler noopHandler;

    @Inject @Assisted
    @Getter
    private Channel channel;

    @Inject @Named("identifier")
    @Getter @Setter
    private Protocol protocol;

    @Getter
    private ProtocolState state = HANDSHAKE;

    private static final Random random = new Random();

    @Getter
    private final String sessionId = Long.toString(random.nextLong(), 16).trim();

    @Getter @Setter
    private String verifyUsername;

    @Getter @Setter
    private byte[] verifyToken;

    @Getter @Setter
    private PlayerProfile profile;

    @Getter
    private final AttributeStorage attributes;

    @Inject
    public Session(AttributeRegistry attributeRegistry) {
        this.attributes = attributeRegistry.get(this);
    }

    public ChannelFuture send(Packet packet) {
        return channel.writeAndFlush(packet);
    }

    public void disconnect() {
        channel.close();
    }

    public void disconnect(Packet packet) {
        channel.writeAndFlush(packet)
                .addListener(future -> channel.close());
    }

    public void enableEncryption(SecretKey sharedSecret) {
        updatePipeline("encryption", handlerFactory.createEncryptionHandler(sharedSecret));
    }

    public void enableCompression(int threshold) {
        updatePipeline("compression", new CompressionHandler(threshold));
    }

    public void disableCompression() {
        updatePipeline("compression", noopHandler);
    }

    private void updatePipeline(String key, ChannelHandler handler) {
        channel.pipeline().replace(key, key, handler);
    }

    public void setState(ProtocolState state) {
        this.state = state;
    }

    @Override
    public void observe(Packet packet) {
        send(packet);
    }
}
