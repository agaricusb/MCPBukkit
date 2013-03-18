package org.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;

public abstract class CraftProjectile extends AbstractProjectile implements Projectile {
    public CraftProjectile(CraftServer server, net.minecraft.entity.Entity entity) {
        super(server, entity);
    }

    public LivingEntity getShooter() {
        if (getHandle().func_85052_h() instanceof net.minecraft.entity.EntityLiving) {
            return (LivingEntity) getHandle().func_85052_h().getBukkitEntity();
        }

        return null;
    }

    public void setShooter(LivingEntity shooter) {
        if (shooter instanceof CraftLivingEntity) {
            getHandle().field_70192_c = (net.minecraft.entity.EntityLiving) ((CraftLivingEntity) shooter).entity;
            if (shooter instanceof CraftHumanEntity) {
                getHandle().field_85053_h = ((CraftHumanEntity) shooter).getName();
            }
        }
    }

    @Override
    public net.minecraft.entity.projectile.EntityThrowable getHandle() {
        return (net.minecraft.entity.projectile.EntityThrowable) entity;
    }

    @Override
    public String toString() {
        return "CraftProjectile";
    }
}
