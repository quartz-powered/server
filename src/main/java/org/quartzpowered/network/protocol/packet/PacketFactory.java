package org.quartzpowered.network.protocol.packet;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import lombok.Getter;

public class PacketFactory<T extends Packet> {
    private static final ClassValue<PacketFactory> FACTORIES = new ClassValue<PacketFactory>() {
        @Override
        protected PacketFactory computeValue(Class<?> type) {
            return new PacketFactory<>(type.asSubclass(Packet.class));
        }
    };

    public static PacketFactory get(Class<? extends Packet> type) {
        return FACTORIES.get(type);
    }

    @Getter
    private final Class<T> type;
    private final ConstructorAccess<T> constructorAccess;

    private PacketFactory(Class<T> type) {
        this.type = type;
        this.constructorAccess = ConstructorAccess.get(type);
    }

    public T create() {
        return constructorAccess.newInstance();
    }
}
