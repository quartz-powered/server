package org.quartzpowered.protocol.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.quartzpowered.network.inject.NetworkModule;
import org.quartzpowered.network.protocol.Protocol;
import org.quartzpowered.network.protocol.ProtocolRegistry;
import org.quartzpowered.protocol.codec.indentifier.IdentifierProtocol;
import org.quartzpowered.protocol.codec.v1_8_R1.ProtocolV1_8_R1;

import javax.inject.Named;

public class ProtocolModule extends AbstractModule {
    private final IdentifierProtocol identifierProtocol = new IdentifierProtocol();

    @Override
    protected void configure() {
        install(new NetworkModule());

        bind(ProtocolRegistry.class).toInstance(new ProtocolRegistry() {
            {
                register(new ProtocolV1_8_R1());
            }
        });
    }

    @Provides @Named("identifier")
    public Protocol provideIdentifierProtocol() {
        return identifierProtocol;
    }
}
