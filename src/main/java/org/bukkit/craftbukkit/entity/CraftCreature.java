package org.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;

public class CraftCreature extends CraftLivingEntity implements Creature {
    public CraftCreature(CraftServer server, net.minecraft.entity.EntityCreature entity) {
        super(server, entity);
    }

    public void setTarget(LivingEntity target) {
        net.minecraft.entity.EntityCreature entity = getHandle();
        if (target == null) {
            entity.field_70789_a = null;
        } else if (target instanceof CraftLivingEntity) {
            entity.field_70789_a = ((CraftLivingEntity) target).getHandle();
            entity.field_70786_d = entity.field_70170_p.func_72865_a(entity, entity.field_70789_a, 16.0F, true, false, false, true);
        }
    }

    public CraftLivingEntity getTarget() {
        if (getHandle().field_70789_a == null) return null;
        if (!(getHandle().field_70789_a instanceof net.minecraft.entity.EntityLiving)) return null;

        return (CraftLivingEntity) getHandle().field_70789_a.getBukkitEntity();
    }

    @Override
    public net.minecraft.entity.EntityCreature getHandle() {
        return (net.minecraft.entity.EntityCreature) entity;
    }

    @Override
    public String toString() {
        return "CraftCreature";
    }
}
