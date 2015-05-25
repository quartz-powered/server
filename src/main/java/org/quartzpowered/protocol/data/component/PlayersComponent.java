package org.quartzpowered.protocol.data.component;

import lombok.Data;

import java.util.List;

@Data
public class PlayersComponent {
    private int max;
    private int online;
    private List<PlayerComponent> sample;
}
