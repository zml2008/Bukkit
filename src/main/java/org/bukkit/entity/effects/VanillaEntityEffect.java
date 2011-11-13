package org.bukkit.entity.effects;

/**
 * @author zml2008
 */
public class VanillaEntityEffect extends EntityEffect {
    private VanillaEntityEffects effect;
    private int duration, amplitude;

    public VanillaEntityEffect(VanillaEntityEffects effect, int duration, int amplitude) {
        this.effect = effect;
        this.duration = duration;
        this.amplitude = amplitude;
    }

    public int getDuration() {
        return duration;
    }

    public int getAmplitude() {
        return amplitude;
    }

    public VanillaEntityEffects getEffect() {
        return effect;
    }
}
