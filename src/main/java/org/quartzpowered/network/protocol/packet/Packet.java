package org.quartzpowered.network.protocol.packet;

import lombok.Data;
import org.quartzpowered.network.session.Session;

@Data
public class Packet {
    private Session sender;
}
