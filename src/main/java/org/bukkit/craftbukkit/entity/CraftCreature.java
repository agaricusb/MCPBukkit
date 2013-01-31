package org.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;

public class CraftCreature extends CraftLivingEntity implements Creature {
    public CraftCreature(CraftServer server, net.minecraft.entity.EntityCreature/*was:EntityCreature*/ entity) {
        super(server, entity);
    }

    public void setTarget(LivingEntity target) {
        net.minecraft.entity.EntityCreature/*was:EntityCreature*/ entity = getHandle();
        if (target == null) {
            entity.field_70789_a/*was:target*/ = null;
        } else if (target instanceof CraftLivingEntity) {
            entity.field_70789_a/*was:target*/ = ((CraftLivingEntity) target).getHandle();
            entity.field_70786_d/*was:pathEntity*/ = entity.field_70170_p/*was:world*/.func_72865_a/*was:findPath*/(entity, entity.field_70789_a/*was:target*/, 16.0F, true, false, false, true);
        }
    }

    public CraftLivingEntity getTarget() {
        if (getHandle().field_70789_a/*was:target*/ == null) return null;
        if (!(getHandle().field_70789_a/*was:target*/ instanceof net.minecraft.entity.EntityLiving/*was:EntityLiving*/)) return null;

        return (CraftLivingEntity) getHandle().field_70789_a/*was:target*/.getBukkitEntity();
    }

    @Override
    public net.minecraft.entity.EntityCreature/*was:EntityCreature*/ getHandle() {
        return (net.minecraft.entity.EntityCreature/*was:EntityCreature*/) entity;
    }

    @Override
    public String toString() {
        return "CraftCreature";
    }
}
