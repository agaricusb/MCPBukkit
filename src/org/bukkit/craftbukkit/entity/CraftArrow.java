package org.bukkit.craftbukkit.entity;


import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public class CraftArrow extends AbstractProjectile implements Arrow {

    public CraftArrow(CraftServer server, net.minecraft.entity.projectile.EntityArrow entity) {
        super(server, entity);
    }

    public LivingEntity getShooter() {
        if (getHandle().field_70250_c != null) {
            return (LivingEntity) getHandle().field_70250_c.getBukkitEntity();
        }

        return null;
    }

    public void setShooter(LivingEntity shooter) {
        if (shooter instanceof CraftLivingEntity) {
            getHandle().field_70250_c = ((CraftLivingEntity) shooter).getHandle();
        }
    }

    @Override
    public net.minecraft.entity.projectile.EntityArrow getHandle() {
        return (net.minecraft.entity.projectile.EntityArrow) entity;
    }

    @Override
    public String toString() {
        return "CraftArrow";
    }

    public EntityType getType() {
        return EntityType.ARROW;
    }
}
