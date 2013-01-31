package org.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;

public class CraftExperienceOrb extends CraftEntity implements ExperienceOrb {
    public CraftExperienceOrb(CraftServer server, net.minecraft.entity.item.EntityXPOrb/*was:EntityExperienceOrb*/ entity) {
        super(server, entity);
    }

    public int getExperience() {
        return getHandle().field_70530_e/*was:value*/;
    }

    public void setExperience(int value) {
        getHandle().field_70530_e/*was:value*/ = value;
    }

    @Override
    public net.minecraft.entity.item.EntityXPOrb/*was:EntityExperienceOrb*/ getHandle() {
        return (net.minecraft.entity.item.EntityXPOrb/*was:EntityExperienceOrb*/) entity;
    }

    @Override
    public String toString() {
        return "CraftExperienceOrb";
    }

    public EntityType getType() {
        return EntityType.EXPERIENCE_ORB;
    }
}
