package org.bukkit.craftbukkit.entity;


import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.WaterMob;

public class CraftWaterMob extends CraftCreature implements WaterMob {

    public CraftWaterMob(CraftServer server, net.minecraft.entity.passive.EntityWaterMob/*was:EntityWaterAnimal*/ entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.entity.passive.EntityWaterMob/*was:EntityWaterAnimal*/ getHandle() {
        return (net.minecraft.entity.passive.EntityWaterMob/*was:EntityWaterAnimal*/) entity;
    }

    @Override
    public String toString() {
        return "CraftWaterMob";
    }
}
