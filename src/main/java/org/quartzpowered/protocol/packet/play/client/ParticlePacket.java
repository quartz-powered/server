package org.quartzpowered.protocol.packet.play.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.protocol.data.Particle;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ParticlePacket extends Packet {
    private Particle particle;
    private boolean longDistance;
    private float x;
    private float y;
    private float z;
    private float offsetX;
    private float offsetY;
    private float offsetZ;
    private float particleData;
    private int particleCount;
    private List<Integer> data;
}
