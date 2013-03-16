package org.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.ComplexEntityPart;
import org.bukkit.entity.ComplexLivingEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent;

public class CraftComplexPart extends CraftEntity implements ComplexEntityPart {
    public CraftComplexPart(CraftServer server, net.minecraft.entity.boss.EntityDragonPart entity) {
        super(server, entity);
    }

    public ComplexLivingEntity getParent() {
        return (ComplexLivingEntity) ((net.minecraft.entity.boss.EntityDragon) getHandle().field_70259_a).getBukkitEntity();
    }

    @Override
    public void setLastDamageCause(EntityDamageEvent cause) {
        getParent().setLastDamageCause(cause);
    }

    @Override
    public EntityDamageEvent getLastDamageCause() {
        return getParent().getLastDamageCause();
    }

    @Override
    public net.minecraft.entity.boss.EntityDragonPart getHandle() {
        return (net.minecraft.entity.boss.EntityDragonPart) entity;
    }

    @Override
    public String toString() {
        return "CraftComplexPart";
    }

    public EntityType getType() {
        return EntityType.COMPLEX_PART;
    }
}
