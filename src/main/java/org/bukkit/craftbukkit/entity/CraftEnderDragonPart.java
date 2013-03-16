package org.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EnderDragonPart;
import org.bukkit.entity.Entity;

public class CraftEnderDragonPart extends CraftComplexPart implements EnderDragonPart {
    public CraftEnderDragonPart(CraftServer server, net.minecraft.entity.boss.EntityDragonPart entity) {
        super(server, entity);
    }

    @Override
    public EnderDragon getParent() {
        return (EnderDragon) super.getParent();
    }

    @Override
    public net.minecraft.entity.boss.EntityDragonPart getHandle() {
        return (net.minecraft.entity.boss.EntityDragonPart) entity;
    }

    @Override
    public String toString() {
        return "CraftEnderDragonPart";
    }

    public void damage(int amount) {
        getParent().damage(amount);
    }

    public void damage(int amount, Entity source) {
        getParent().damage(amount, source);
    }

    public int getHealth() {
        return getParent().getHealth();
    }

    public void setHealth(int health) {
        getParent().setHealth(health);
    }

    public int getMaxHealth() {
        return getParent().getMaxHealth();
    }

    public void setMaxHealth(int health) {
        getParent().setMaxHealth(health);
    }

    public void resetMaxHealth() {
        getParent().resetMaxHealth();
    }
}
