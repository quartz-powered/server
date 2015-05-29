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
