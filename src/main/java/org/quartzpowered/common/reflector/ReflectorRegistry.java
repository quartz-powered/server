package org.quartzpowered.common.reflector;

import org.quartzpowered.common.factory.Factory;

import javax.inject.Singleton;

@Singleton
public class ReflectorRegistry {
    private final ClassValue<Reflector<Object>> reflectors = new ClassValue<Reflector<Object>>() {
        @Override
        @SuppressWarnings("unchecked")
        protected Reflector<Object> computeValue(Class<?> type) {
            return new Reflector(type);
        }
    };

    @SuppressWarnings("unchecked")
    public <T> Reflector<T> get(Class<? extends T> type) {
        return (Reflector<T>) this.reflectors.get(type);
    }
}
