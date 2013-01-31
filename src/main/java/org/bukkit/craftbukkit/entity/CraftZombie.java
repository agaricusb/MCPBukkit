package org.bukkit.craftbukkit.entity;


import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;

public class CraftZombie extends CraftMonster implements Zombie {

    public CraftZombie(CraftServer server, net.minecraft.entity.monster.EntityZombie/*was:EntityZombie*/ entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.entity.monster.EntityZombie/*was:EntityZombie*/ getHandle() {
        return (net.minecraft.entity.monster.EntityZombie/*was:EntityZombie*/) entity;
    }

    @Override
    public String toString() {
        return "CraftZombie";
    }

    public EntityType getType() {
        return EntityType.ZOMBIE;
    }

    public boolean isBaby() {
        return getHandle().func_70631_g_/*was:isBaby*/();
    }

    public void setBaby(boolean flag) {
        getHandle().func_82227_f/*was:setBaby*/(flag);
    }

    public boolean isVillager() {
        return getHandle().func_82231_m/*was:isVillager*/();
    }

    public void setVillager(boolean flag) {
        getHandle().func_82229_g/*was:setVillager*/(flag);
    }
}
