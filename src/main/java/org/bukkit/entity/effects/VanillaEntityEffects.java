package org.bukkit.entity.effects;

import java.util.HashMap;
import java.util.Map;

public enum VanillaEntityEffects {

    MOVE_SPEED(1),
    MOVE_SLOW(2),
    DIG_SPEED(3),
    DIG_SLOW(4),
    DAMAGE_BOOST(5),
    HEAL(6),
    HARM(7),
    JUMP(8),
    CONFUSION(9),
    REGENERATION(10),
    RESISTANCE(11),
    FIRE_RESISTANCE(12),
    WATER_BREATHING(13),
    INVISIBILITY(14),
    BLINDNESS(15),
    NIGHT_VISION(16),
    HUNGER(17),
    WEAKNESS(18),
    POISON(19);

    private final byte id;

    private static final Map<Integer, VanillaEntityEffects> lookup = new HashMap<Integer, VanillaEntityEffects>();

    static {
        for (VanillaEntityEffects effect : values()) {
            lookup.put((int)effect.getId(), effect);
        }
    }

    public static VanillaEntityEffects getById(int id) {
        return lookup.get(id);
    }

    private VanillaEntityEffects(int id) {
        this.id = (byte) id;
    }

    public byte getId() {
        return id;
    }
    
}
