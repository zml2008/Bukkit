package org.bukkit.entity.effects;

/**
 * @author zml2008
 */
public abstract class EntityEffect {
    private EntityEffectApplier applier;

    public EntityEffectApplier getApplier() {
        return applier;
    }

    public void setApplier(EntityEffectApplier applier) {
        this.applier = applier;
    }
}
