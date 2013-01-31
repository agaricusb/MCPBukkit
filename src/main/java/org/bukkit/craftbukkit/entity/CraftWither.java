package org.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Wither;
import org.bukkit.entity.EntityType;

public class CraftWither extends CraftMonster implements Wither {
    public CraftWither(CraftServer server, net.minecraft.entity.boss.EntityWither/*was:EntityWither*/ entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.entity.boss.EntityWither/*was:EntityWither*/ getHandle() {
        return (net.minecraft.entity.boss.EntityWither/*was:EntityWither*/) entity;
    }

    @Override
    public String toString() {
        return "CraftWither";
    }

    public EntityType getType() {
        return EntityType.WITHER;
    }
}
