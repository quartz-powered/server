package org.quartzpowered.protocol.data.component;

import lombok.Data;

@Data
public class VersionComponent {
    private String name;
    private int protocol;
}
