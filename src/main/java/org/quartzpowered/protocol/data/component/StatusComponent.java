package org.quartzpowered.protocol.data.component;

import lombok.Data;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

@Data
public class StatusComponent {
    private static final ObjectMapper objectMapper = JsonFactory.create();

    private VersionComponent version;
    private PlayersComponent players;
    private TextComponent description;

    public String toJson() {
        return objectMapper.toJson(this);
    }

    public static StatusComponent fromJson(String json) {
        return objectMapper.fromJson(json, StatusComponent.class);
    }
}
