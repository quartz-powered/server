package org.quartzpowered.protocol.packet.play.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.engine.object.component.Player;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.protocol.data.info.PlayerInfo;
import org.quartzpowered.protocol.data.info.PlayerInfoAction;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlayerInfoPacket extends Packet {
    private PlayerInfoAction action;
    private List<PlayerInfo> info;
}
