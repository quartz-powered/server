/**
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
package org.quartzpowered.protocol.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.quartzpowered.network.inject.NetworkModule;
import org.quartzpowered.network.protocol.Protocol;
import org.quartzpowered.network.protocol.ProtocolRegistry;
import org.quartzpowered.protocol.codec.indentifier.IdentifierProtocol;
import org.quartzpowered.protocol.codec.v1_8_R1.ProtocolV1_8_R1;

import javax.inject.Named;

public class ProtocolModule extends AbstractModule {
    private final IdentifierProtocol identifierProtocol = new IdentifierProtocol();

    @Override
    protected void configure() {
        install(new NetworkModule());

        bind(ProtocolRegistry.class).toInstance(new ProtocolRegistry() {
            {
                register(new ProtocolV1_8_R1());
            }
        });
    }

    @Provides @Named("identifier")
    public Protocol provideIdentifierProtocol() {
        return identifierProtocol;
    }
}
