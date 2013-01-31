package org.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.ComplexLivingEntity;

public abstract class CraftComplexLivingEntity extends CraftLivingEntity implements ComplexLivingEntity {
    public CraftComplexLivingEntity(CraftServer server, net.minecraft.entity.EntityLiving/*was:EntityLiving*/ entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.entity.EntityLiving/*was:EntityLiving*/ getHandle() {
        return (net.minecraft.entity.EntityLiving/*was:EntityLiving*/) entity;
    }

    @Override
    public String toString() {
        return "CraftComplexLivingEntity";
    }
}
