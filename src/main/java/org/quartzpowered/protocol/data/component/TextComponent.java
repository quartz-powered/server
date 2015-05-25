package org.quartzpowered.protocol.data.component;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class TextComponent {
    private static final ObjectMapper objectMapper = JsonFactory.create();

    private String text;

    public TextComponent(String text) {
        this.text = text;
    }

    public String toJson() {
        return objectMapper.toJson(this);
    }

    public static TextComponent fromJson(String json) {
        return objectMapper.fromJson(json, TextComponent.class);
    }
}
