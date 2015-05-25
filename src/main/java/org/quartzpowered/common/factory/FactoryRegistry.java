package org.quartzpowered.common.factory;

import org.quartzpowered.engine.Component;

import javax.inject.Singleton;

@Singleton
public class FactoryRegistry {
    private final ClassValue<Factory> FACTORIES = new ClassValue<Factory>() {
        @Override
        @SuppressWarnings("unchecked")
        protected Factory computeValue(Class<?> type) {
            return new Factory(type);
        }
    };

    @SuppressWarnings("unchecked")
    public <T> Factory<T> get(Class<? extends T> type) {
        return (Factory<T>) FACTORIES.get(type);
    }
}
