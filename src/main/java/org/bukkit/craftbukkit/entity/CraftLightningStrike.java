package org.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LightningStrike;

public class CraftLightningStrike extends CraftEntity implements LightningStrike {
    public CraftLightningStrike(final CraftServer server, final net.minecraft.entity.effect.EntityLightningBolt/*was:EntityLightning*/ entity) {
        super(server, entity);
    }

    public boolean isEffect() {
        return ((net.minecraft.entity.effect.EntityLightningBolt/*was:EntityLightning*/) super.getHandle()).isEffect;
    }

    @Override
    public net.minecraft.entity.effect.EntityLightningBolt/*was:EntityLightning*/ getHandle() {
        return (net.minecraft.entity.effect.EntityLightningBolt/*was:EntityLightning*/) entity;
    }

    @Override
    public String toString() {
        return "CraftLightningStrike";
    }

    public EntityType getType() {
        return EntityType.LIGHTNING;
    }
}
