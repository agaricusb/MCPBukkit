package org.bukkit.craftbukkit.entity;


import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;

public class CraftZombie extends CraftMonster implements Zombie {

    public CraftZombie(CraftServer server, net.minecraft.entity.monster.EntityZombie entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.entity.monster.EntityZombie getHandle() {
        return (net.minecraft.entity.monster.EntityZombie) entity;
    }

    @Override
    public String toString() {
        return "CraftZombie";
    }

    public EntityType getType() {
        return EntityType.ZOMBIE;
    }

    public boolean isBaby() {
        return getHandle().func_70631_g_();
    }

    public void setBaby(boolean flag) {
        getHandle().func_82227_f(flag);
    }

    public boolean isVillager() {
        return getHandle().func_82231_m();
    }

    public void setVillager(boolean flag) {
        getHandle().func_82229_g(flag);
    }
}
