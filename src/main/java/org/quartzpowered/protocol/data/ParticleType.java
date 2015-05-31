/**
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

public enum ParticleType {
    EXPLODE,
    LARGE_EXPLOSION,
    HUGE_EXPLOSION,
    FIREWORKS_SPARK,
    BUBBLE,
    SPLASH,
    WAKE,
    SUSPENDED,
    DEATH_SUSPEND,
    CRIT,
    MAGIC_CRIT,
    SMOKE,
    LARGE_SMOKE,
    SPELL,
    INSTANT_SPELL,
    MOB_SPELL,
    MOB_SPELL_AMBIENT,
    WITCH_MAGIC,
    DRIP_WATER,
    DROP_LAVA,
    ANGRY_VILLAGER,
    HAPPY_VILLAGER,
    TOWN_AURA,
    NOTE,
    PORTAL,
    ENCHANTMENT_TABLE,
    FLAME,
    LAVA,
    FOOTSTEP,
    CLOUD,
    RED_DUST,
    SNOWBALL_POOF,
    SNOW_SHOVEL,
    SLIME,
    HEART,
    BARRIER,
    ICON_CRACK,
    BLOCK_CRACK,
    BLOCK_DUST,
    DROPLET,
    TAKE,
    MOB_APPEARANCE;

    public int getId() {
        return ordinal();
    }

    public static ParticleType fromId(int id) {
        return values()[id];
    }
}
