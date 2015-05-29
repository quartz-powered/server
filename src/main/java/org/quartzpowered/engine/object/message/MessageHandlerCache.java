/*
 * This file is a component of Quartz Powered, this license makes sure any work
 * associated with Quartz Powered, must follow the conditions of the license included.
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Quartz Powered
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
