package org.bukkit.craftbukkit.entity;


import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Monster;

public class CraftMonster extends CraftCreature implements Monster {

    public CraftMonster(CraftServer server, net.minecraft.entity.monster.EntityMob/*was:EntityMonster*/ entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.entity.monster.EntityMob/*was:EntityMonster*/ getHandle() {
        return (net.minecraft.entity.monster.EntityMob/*was:EntityMonster*/) entity;
    }

    @Override
    public String toString() {
        return "CraftMonster";
    }
}
