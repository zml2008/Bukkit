package org.bukkit.entity.effects;

import org.bukkit.entity.LivingEntity;

/**
 * @author zml2008
 */
public interface EntityEffectApplier {
    public void apply(LivingEntity entity);

    public void remove(LivingEntity entity);
}
