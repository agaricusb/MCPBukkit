package org.bukkit.craftbukkit.entity;


import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class CraftFireball extends AbstractProjectile implements Fireball {
    public CraftFireball(CraftServer server, net.minecraft.entity.projectile.EntityFireball/*was:EntityFireball*/ entity) {
        super(server, entity);
    }

    public float getYield() {
        return getHandle().yield;
    }

    public boolean isIncendiary() {
        return getHandle().isIncendiary;
    }

    public void setIsIncendiary(boolean isIncendiary) {
        getHandle().isIncendiary = isIncendiary;
    }

    public void setYield(float yield) {
        getHandle().yield = yield;
    }

    public LivingEntity getShooter() {
        if (getHandle().field_70235_a/*was:shooter*/ != null) {
            return (LivingEntity) getHandle().field_70235_a/*was:shooter*/.getBukkitEntity();
        }

        return null;
    }

    public void setShooter(LivingEntity shooter) {
        if (shooter instanceof CraftLivingEntity) {
            getHandle().field_70235_a/*was:shooter*/ = (net.minecraft.entity.EntityLiving/*was:EntityLiving*/) ((CraftLivingEntity) shooter).entity;
        }
    }

    public Vector getDirection() {
        return new Vector(getHandle().field_70232_b/*was:dirX*/, getHandle().field_70233_c/*was:dirY*/, getHandle().field_70230_d/*was:dirZ*/);
    }

    public void setDirection(Vector direction) {
        getHandle().setDirection(direction.getX(), direction.getY(), direction.getZ());
    }

    @Override
    public net.minecraft.entity.projectile.EntityFireball/*was:EntityFireball*/ getHandle() {
        return (net.minecraft.entity.projectile.EntityFireball/*was:EntityFireball*/) entity;
    }

    @Override
    public String toString() {
        return "CraftFireball";
    }

    public EntityType getType() {
        return EntityType.UNKNOWN;
    }
}
