package org.bukkit.craftbukkit.entity;

import java.util.Collection;


import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffect;

public class CraftThrownPotion extends CraftProjectile implements ThrownPotion {
    private Collection<PotionEffect> effects = null;

    public CraftThrownPotion(CraftServer server, net.minecraft.entity.projectile.EntityPotion/*was:EntityPotion*/ entity) {
        super(server, entity);
    }

    public Collection<PotionEffect> getEffects() {
        if (effects == null) {
            effects = Potion.getBrewer().getEffectsFromDamage(getHandle().func_70196_i/*was:getPotionValue*/());
        }

        return effects;
    }

    @Override
    public net.minecraft.entity.projectile.EntityPotion/*was:EntityPotion*/ getHandle() {
        return (net.minecraft.entity.projectile.EntityPotion/*was:EntityPotion*/) entity;
    }

    @Override
    public String toString() {
        return "CraftThrownPotion";
    }

    public EntityType getType() {
        return EntityType.SPLASH_POTION;
    }
}
