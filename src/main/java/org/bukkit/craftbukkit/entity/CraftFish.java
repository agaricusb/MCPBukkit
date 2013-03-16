package org.bukkit.craftbukkit.entity;


import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fish;
import org.bukkit.entity.LivingEntity;

public class CraftFish extends AbstractProjectile implements Fish {
    public CraftFish(CraftServer server, net.minecraft.entity.projectile.EntityFishHook entity) {
        super(server, entity);
    }

    public LivingEntity getShooter() {
        if (getHandle().field_70204_b != null) {
            return (LivingEntity) getHandle().field_70204_b.getBukkitEntity();
        }

        return null;
    }

    public void setShooter(LivingEntity shooter) {
        if (shooter instanceof CraftHumanEntity) {
            getHandle().field_70204_b = (net.minecraft.entity.player.EntityPlayer) ((CraftHumanEntity) shooter).entity;
        }
    }

    @Override
    public net.minecraft.entity.projectile.EntityFishHook getHandle() {
        return (net.minecraft.entity.projectile.EntityFishHook) entity;
    }

    @Override
    public String toString() {
        return "CraftFish";
    }

    public EntityType getType() {
        return EntityType.FISHING_HOOK;
    }
}
