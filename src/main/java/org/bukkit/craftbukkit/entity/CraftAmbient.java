package org.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Ambient;
import org.bukkit.entity.EntityType;

public class CraftAmbient extends CraftLivingEntity implements Ambient {
    public CraftAmbient(CraftServer server, net.minecraft.entity.passive.EntityAmbientCreature/*was:EntityAmbient*/ entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.entity.passive.EntityAmbientCreature/*was:EntityAmbient*/ getHandle() {
        return (net.minecraft.entity.passive.EntityAmbientCreature/*was:EntityAmbient*/) entity;
    }

    @Override
    public String toString() {
        return "CraftAmbient";
    }

    public EntityType getType() {
        return EntityType.UNKNOWN;
    }
}
