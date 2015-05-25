package org.quartzpowered.server.inject;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import org.quartzpowered.engine.inject.EngineModule;
import org.quartzpowered.protocol.inject.ProtocolModule;
import org.quartzpowered.server.network.auth.AuthRunnableFactory;

public class ServerModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new ProtocolModule());
        install(new EngineModule());
        install(new FactoryModuleBuilder().build(AuthRunnableFactory.class));
    }
}
