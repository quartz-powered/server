package org.quartzpowered.engine.inject;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.name.Names;
import org.quartzpowered.engine.component.GameObjectFactory;
import org.quartzpowered.engine.observe.Observer;
import org.quartzpowered.engine.observe.ObserverFactory;
import org.quartzpowered.engine.observe.SessionObserver;

public class EngineModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().build(GameObjectFactory.class));
        install(new FactoryModuleBuilder()
                .implement(Observer.class, Names.named("session"), SessionObserver.class)
                .build(ObserverFactory.class));
    }
}
