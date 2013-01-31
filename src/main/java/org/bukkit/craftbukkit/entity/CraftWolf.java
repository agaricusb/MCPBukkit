package org.bukkit.craftbukkit.entity;

import org.bukkit.DyeColor;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wolf;

public class CraftWolf extends CraftTameableAnimal implements Wolf {
    public CraftWolf(CraftServer server, net.minecraft.entity.passive.EntityWolf/*was:EntityWolf*/ wolf) {
        super(server, wolf);
    }

    public boolean isAngry() {
        return getHandle().func_70919_bu/*was:isAngry*/();
    }

    public void setAngry(boolean angry) {
        getHandle().func_70916_h/*was:setAngry*/(angry);
    }

    @Override
    public net.minecraft.entity.passive.EntityWolf/*was:EntityWolf*/ getHandle() {
        return (net.minecraft.entity.passive.EntityWolf/*was:EntityWolf*/) entity;
    }

    @Override
    public EntityType getType() {
        return EntityType.WOLF;
    }

    public DyeColor getCollarColor() {
        return DyeColor.getByWoolData((byte) getHandle().func_82186_bH/*was:getCollarColor*/());
    }

    public void setCollarColor(DyeColor color) {
        getHandle().func_82185_r/*was:setCollarColor*/(color.getWoolData());
    }
}
