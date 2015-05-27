package org.quartzpowered.protocol.codec.v1_8_R1.play.client;

import org.quartzpowered.network.buffer.Buffer;
import org.quartzpowered.network.protocol.codec.Codec;
import org.quartzpowered.network.session.profile.PlayerProfile;
import org.quartzpowered.network.session.profile.PlayerProperty;
import org.quartzpowered.protocol.data.Gamemode;
import org.quartzpowered.protocol.data.component.TextComponent;
import org.quartzpowered.protocol.data.info.PlayerInfo;
import org.quartzpowered.protocol.data.info.PlayerInfoAction;
import org.quartzpowered.protocol.packet.play.client.PlayerInfoPacket;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerInfoCodec implements Codec<PlayerInfoPacket> {
    @Override
    public void encode(Buffer buffer, PlayerInfoPacket packet) {
        PlayerInfoAction action = packet.getAction();
        List<PlayerInfo> info = packet.getInfo();

        buffer.writeVarInt(action.getId());
        buffer.writeVarInt(info.size());

        for (PlayerInfo entry : info) {
            PlayerProfile profile = entry.getProfile();
            boolean hasDisplayName = entry.hasDisplayName();
            List<PlayerProperty> properties = profile.getProperties();

            buffer.writeUuid(profile.getUniqueId());

            switch (action) {
                case ADD:
                    buffer.writeString(profile.getName());
                    buffer.writeVarInt(properties.size());

                    for (PlayerProperty property : properties) {
                        boolean isSigned = property.isSigned();

                        buffer.writeString(property.getName());
                        buffer.writeString(property.getValue());
                        buffer.writeBoolean(isSigned);

                        if (isSigned) {
                            buffer.writeString(property.getSignature());
                        }
                    }

                    buffer.writeVarInt(entry.getGamemode().getId());
                    buffer.writeVarInt(entry.getPing());

                    buffer.writeBoolean(hasDisplayName);
                    if (hasDisplayName) {
                        buffer.writeString(entry.getDisplayName().toJson());
                    }
                    break;

                case UPDATE_GAMEMODE:
                    buffer.writeVarInt(entry.getGamemode().getId());
                    break;

                case UPDATE_LATENCY:
                    buffer.writeVarInt(entry.getPing());
                    break;

                case UPDATE_DISPLAY_NAME:
                    buffer.writeBoolean(hasDisplayName);
                    if (hasDisplayName) {
                        buffer.writeString(entry.getDisplayName().toJson());
                    }
                    break;

                case REMOVE:
                    break;
            }
        }
    }

    @Override
    public void decode(Buffer buffer, PlayerInfoPacket packet) {
        PlayerInfoAction action = PlayerInfoAction.fromId(buffer.readVarInt());

        List<PlayerInfo> info = new ArrayList<>(buffer.readVarInt());

        for (int i = 0; i < info.size(); i++) {
            PlayerInfo entry = new PlayerInfo();

            UUID playerId = buffer.readUuid();

            switch (action) {
                case ADD:
                    String name = buffer.readString();
                    List<PlayerProperty> properties = new ArrayList<>(buffer.readVarInt());
                    for (int j = 0; j < properties.size(); j++) {
                        String propertyName = buffer.readString();
                        String propertyValue = buffer.readString();
                        String signature = null;
                        if (buffer.readBoolean()) {
                            signature = buffer.readString();
                        }

                        properties.set(j, new PlayerProperty(propertyName, propertyValue, signature));
                    }
                    entry.setProfile(new PlayerProfile(name, playerId, properties));

                    entry.setGamemode(Gamemode.fromId(buffer.readVarInt()));
                    entry.setPing(buffer.readVarInt());

                    if (buffer.readBoolean()) {
                        entry.setDisplayName(TextComponent.fromJson(buffer.readString()));
                    }
                    break;

                case UPDATE_GAMEMODE:
                    entry.setGamemode(Gamemode.fromId(buffer.readVarInt()));
                    break;

                case UPDATE_LATENCY:
                    entry.setPing(buffer.readVarInt());
                    break;

                case UPDATE_DISPLAY_NAME:
                    if (buffer.readBoolean()) {
                        entry.setDisplayName(TextComponent.fromJson(buffer.readString()));
                    }
                    break;

                case REMOVE:
                    break;
            }
            info.set(i, entry);
        }

        packet.setInfo(info);
    }
}
