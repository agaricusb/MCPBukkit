package org.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;

public abstract class CraftProjectile extends AbstractProjectile implements Projectile {
    public CraftProjectile(CraftServer server, net.minecraft.entity.Entity/*was:net.minecraft.server.Entity*/ entity) {
        super(server, entity);
    }

    public LivingEntity getShooter() {
        if (getHandle().func_85052_h/*was:getShooter*/() instanceof net.minecraft.entity.EntityLiving/*was:EntityLiving*/) {
            return (LivingEntity) getHandle().func_85052_h/*was:getShooter*/().getBukkitEntity();
        }

        return null;
    }

    public void setShooter(LivingEntity shooter) {
        if (shooter instanceof CraftLivingEntity) {
            getHandle().field_70192_c/*was:shooter*/ = (net.minecraft.entity.EntityLiving/*was:EntityLiving*/) ((CraftLivingEntity) shooter).entity;
            if (shooter instanceof CraftHumanEntity) {
                getHandle().field_85053_h/*was:shooterName*/ = ((CraftHumanEntity) shooter).getName();
            }
        }
    }

    @Override
    public net.minecraft.entity.projectile.EntityThrowable/*was:EntityProjectile*/ getHandle() {
        return (net.minecraft.entity.projectile.EntityThrowable/*was:EntityProjectile*/) entity;
    }

    @Override
    public String toString() {
        return "CraftProjectile";
    }
}
