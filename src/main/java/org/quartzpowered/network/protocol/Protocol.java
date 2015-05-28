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
package org.quartzpowered.network.protocol;

import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.network.protocol.codec.CodecRegistry;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.network.protocol.packet.PacketRegistry;

import java.util.EnumMap;
import java.util.Map;

public abstract class Protocol {
    private final Map<ProtocolState, PacketRegistry> serverBoundPackets = new EnumMap<>(ProtocolState.class);
    private final Map<ProtocolState, PacketRegistry> clientBoundPackets = new EnumMap<>(ProtocolState.class);

    private final Map<ProtocolState, CodecRegistry> serverBoundCodecs = new EnumMap<>(ProtocolState.class);
    private final Map<ProtocolState, CodecRegistry> clientBoundCodecs = new EnumMap<>(ProtocolState.class);

    public Protocol() {
        registerPackets();
    }

    protected <T extends Packet> void serverBoundPacket(ProtocolState state, int id, Class<T> type, Codec<T> codec) {
        serverBoundPackets.computeIfAbsent(state, PacketRegistry::new).register(id, type);
        serverBoundCodecs.computeIfAbsent(state, CodecRegistry::new).register(id, codec);
    }

    protected <T extends Packet> void clientBoundPacket(ProtocolState state, int id, Class<T> type, Codec<T> codec) {
        clientBoundPackets.computeIfAbsent(state, PacketRegistry::new).register(id, type);
        clientBoundCodecs.computeIfAbsent(state, CodecRegistry::new).register(id, codec);
    }

    public abstract String getName();

    public abstract int getVersion();

    protected abstract void registerPackets();

    public PacketRegistry getServerBoundPackets(ProtocolState state) {
        return serverBoundPackets.get(state);
    }

    public PacketRegistry getClientBoundPackets(ProtocolState state) {
        return clientBoundPackets.get(state);
    }

    public CodecRegistry getServerBoundCodecs(ProtocolState state) {
        return serverBoundCodecs.get(state);
    }

    public CodecRegistry getClientBoundCodecs(ProtocolState state) {
        return clientBoundCodecs.get(state);
    }

    @Override
    public String toString() {
        return "Protocol(" +
                "name=" + getName() +
                ", version=" + getVersion() +
                ')';
    }
}
