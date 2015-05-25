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
import org.quartzpowered.engine.observe.ObserverFactory;
import org.quartzpowered.network.codec.CodecFactory;
import org.quartzpowered.network.pipeline.CompressionHandler;
import org.quartzpowered.network.pipeline.EncryptionHandler;
import org.quartzpowered.network.pipeline.HandlerFactory;
import org.quartzpowered.network.pipeline.NoopHandler;
import org.quartzpowered.network.protocol.Protocol;
import org.quartzpowered.network.protocol.ProtocolState;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.network.session.attribute.AttributeStorage;
import org.quartzpowered.network.session.profile.PlayerProfile;

import javax.crypto.SecretKey;
import javax.inject.Inject;

import java.util.Random;
import java.util.concurrent.ExecutionException;

import static org.quartzpowered.network.protocol.ProtocolState.HANDSHAKE;

@ToString(of = {"channel", "profile"})
public class Session {
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
    private final Observer observer;

    @Inject
    public Session(ObserverFactory observerFactory) {
        this.observer = observerFactory.create(this);
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
}
