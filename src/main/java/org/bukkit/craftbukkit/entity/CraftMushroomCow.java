package org.bukkit.craftbukkit.entity;


import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.MushroomCow;

public class CraftMushroomCow extends CraftCow implements MushroomCow {
    public CraftMushroomCow(CraftServer server, net.minecraft.entity.passive.EntityMooshroom/*was:EntityMushroomCow*/ entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.entity.passive.EntityMooshroom/*was:EntityMushroomCow*/ getHandle() {
        return (net.minecraft.entity.passive.EntityMooshroom/*was:EntityMushroomCow*/) entity;
    }

    @Override
    public String toString() {
        return "CraftMushroomCow";
    }

    public EntityType getType() {
        return EntityType.MUSHROOM_COW;
    }
}
