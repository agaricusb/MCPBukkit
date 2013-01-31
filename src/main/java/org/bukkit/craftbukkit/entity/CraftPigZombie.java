package org.bukkit.craftbukkit.entity;


import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PigZombie;

public class CraftPigZombie extends CraftZombie implements PigZombie {

    public CraftPigZombie(CraftServer server, net.minecraft.entity.monster.EntityPigZombie/*was:EntityPigZombie*/ entity) {
        super(server, entity);
    }

    public int getAnger() {
        return getHandle().field_70837_d/*was:angerLevel*/;
    }

    public void setAnger(int level) {
        getHandle().field_70837_d/*was:angerLevel*/ = level;
    }

    public void setAngry(boolean angry) {
        setAnger(angry ? 400 : 0);
    }

    public boolean isAngry() {
        return getAnger() > 0;
    }

    @Override
    public net.minecraft.entity.monster.EntityPigZombie/*was:EntityPigZombie*/ getHandle() {
        return (net.minecraft.entity.monster.EntityPigZombie/*was:EntityPigZombie*/) entity;
    }

    @Override
    public String toString() {
        return "CraftPigZombie";
    }

    public EntityType getType() {
        return EntityType.PIG_ZOMBIE;
    }
}
