package org.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ThrownExpBottle;

public class CraftThrownExpBottle extends CraftProjectile implements ThrownExpBottle {
    public CraftThrownExpBottle(CraftServer server, net.minecraft.entity.item.EntityExpBottle/*was:EntityThrownExpBottle*/ entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.entity.item.EntityExpBottle/*was:EntityThrownExpBottle*/ getHandle() {
        return (net.minecraft.entity.item.EntityExpBottle/*was:EntityThrownExpBottle*/) entity;
    }

    @Override
    public String toString() {
        return "EntityThrownExpBottle";
    }

    public EntityType getType() {
        return EntityType.THROWN_EXP_BOTTLE;
    }
}
