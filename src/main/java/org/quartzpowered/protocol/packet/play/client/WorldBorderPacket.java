package org.quartzpowered.protocol.packet.play.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.protocol.data.WorldBorderAction;

@Data
@EqualsAndHashCode(callSuper = true)
public class WorldBorderPacket extends Packet {
    private WorldBorderAction action;
    private double oldRadius;
    private double newRadius;
    private long speed;
    private double x;
    private double z;
    private int portalTeleportBoundary;
    private int warningTime;
    private int warningBlocks;
}
