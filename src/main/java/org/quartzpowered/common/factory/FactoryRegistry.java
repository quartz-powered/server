package org.quartzpowered.common.factory;

import com.google.inject.Injector;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FactoryRegistry {
    @Inject private Injector injector;

    private final ClassValue<Factory> factories = new ClassValue<Factory>() {
        @Override
        @SuppressWarnings("unchecked")
        protected Factory computeValue(Class<?> type) {
            return new Factory(injector, type);
        }
    };

    @SuppressWarnings("unchecked")
    public <T> Factory<T> get(Class<? extends T> type) {
        return (Factory<T>) factories.get(type);
    }
}
