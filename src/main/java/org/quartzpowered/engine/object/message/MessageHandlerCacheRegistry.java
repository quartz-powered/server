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
