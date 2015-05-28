package org.quartzpowered.engine.object.message;

import lombok.Getter;
import org.quartzpowered.common.reflector.Reflector;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessageHandlerCache<T> {
    @Inject private Logger logger;

    @Getter
    private final Class<T> type;
    private final Reflector<T> reflector;

    private final List<String> listeners = new ArrayList<>();

    public MessageHandlerCache(Class<T> type, Reflector<T> reflector) {
        this.type = type;
        this.reflector = reflector;

        Method[] methods = type.getMethods();
        for (Method method : methods) {
            MessageHandler messageHandler = method.getAnnotation(MessageHandler.class);
            if (messageHandler != null) {
                String name = method.getName();
                if (listeners.contains(name)) {
                    throw new IllegalArgumentException("duplicate MessageHandler: " + name);
                } else {
                    listeners.add(name);
                }
            }
        }
    }

    public boolean hasListener(String listener) {
        return listeners.contains(listener);
    }


    public void sendMessage(T target, String name, Object... args) {
        try {
            reflector.invoke(target, name, args);
        } catch (Throwable throwable) {
            logger.error(String.format("Error while invoking %s with %s on %s", name, Arrays.toString(args), type), throwable);
        }
    }
}
