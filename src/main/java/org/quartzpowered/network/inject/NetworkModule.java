package org.quartzpowered.network.inject;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import org.quartzpowered.common.inject.CommonModule;
import org.quartzpowered.network.codec.CodecFactory;
import org.quartzpowered.network.pipeline.HandlerFactory;
import org.quartzpowered.network.session.SessionFactory;

public class NetworkModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new CommonModule());
        install(new FactoryModuleBuilder().build(SessionFactory.class));
        install(new FactoryModuleBuilder().build(CodecFactory.class));
        install(new FactoryModuleBuilder().build(HandlerFactory.class));
    }
}
