/*
 * This file is a component of Quartz Powered, this license makes sure any work
 * associated with Quartz Powered, must follow the conditions of the license included.
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Quartz Powered
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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