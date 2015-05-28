package org.quartzpowered.protocol.data;

public enum WindowType {
    CHEST(9, "minecraft:chest"),
    WORKBENCH(10, "minecraft:crafting_table"),
    FURNACE(3, "minecraft:furnace"),
    DISPENSER(9, "minecraft:dispenser"),
    ENCHANTING(2, "minecraft:enchanting_table"),
    BREWING(4, "minecraft:brewing_stand"),
    TRADE(3, "minecraft:villager"),
    BEACON(1, "minecraft:beacon"),
    ANVIL(3, "minecraft:anvil"),
    HOPPER(5, "minecraft:hopper"),
    DROPPER(9, "minecraft:dropper"),
    HORSE(3, "EntityHorse");


    private final int size;
    private final String title;

    private WindowType(int defaultSize, String defaultTitle) {
        this.size = defaultSize;
        this.title = defaultTitle;
    }

    public int getSize() {
        return size;
    }

    public String getTitle() {
        return title;
    }

    public static WindowType fromString(String text) {
        if (text != null) {
            for (WindowType b : WindowType.values()) {
                if (text.equalsIgnoreCase(b.getTitle())) {
                    return b;
                }
            }
        }
        return null;
    }

    public int getId() {
        return ordinal();
    }

    public static WindowType fromId(int id) {
        return values()[id];
    }

}