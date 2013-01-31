package org.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.SmallFireball;

public class CraftSmallFireball extends CraftFireball implements SmallFireball {
    public CraftSmallFireball(CraftServer server, net.minecraft.entity.projectile.EntitySmallFireball/*was:EntitySmallFireball*/ entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.entity.projectile.EntitySmallFireball/*was:EntitySmallFireball*/ getHandle() {
        return (net.minecraft.entity.projectile.EntitySmallFireball/*was:EntitySmallFireball*/) entity;
    }

    @Override
    public String toString() {
        return "CraftSmallFireball";
    }

    public EntityType getType() {
        return EntityType.SMALL_FIREBALL;
    }
}
