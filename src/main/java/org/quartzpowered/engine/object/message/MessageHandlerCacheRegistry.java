package org.quartzpowered.engine.object.message;

import com.google.inject.Injector;
import org.quartzpowered.common.reflector.ReflectorRegistry;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MessageHandlerCacheRegistry {
    @Inject private Injector injector;
    @Inject private ReflectorRegistry reflectorRegistry;

    private final ClassValue<MessageHandlerCache<?>> caches = new ClassValue<MessageHandlerCache<?>>() {
        @SuppressWarnings("unchecked")
        @Override
        protected MessageHandlerCache<?> computeValue(Class<?> type) {
            MessageHandlerCache<Object> cache = new MessageHandlerCache<>((Class<Object>) type, reflectorRegistry.get(type));
            injector.injectMembers(cache);
            return cache;
        }
    };

    @SuppressWarnings("unchecked")
    public <T> MessageHandlerCache<T> get(Class<? extends T> type) {
        return (MessageHandlerCache<T>) caches.get(type);
    }
}
