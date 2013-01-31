package org.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Snowman;

public class CraftSnowman extends CraftGolem implements Snowman {
    public CraftSnowman(CraftServer server, net.minecraft.entity.monster.EntitySnowman/*was:EntitySnowman*/ entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.entity.monster.EntitySnowman/*was:EntitySnowman*/ getHandle() {
        return (net.minecraft.entity.monster.EntitySnowman/*was:EntitySnowman*/) entity;
    }

    @Override
    public String toString() {
        return "CraftSnowman";
    }

    public EntityType getType() {
        return EntityType.SNOWMAN;
    }
}
