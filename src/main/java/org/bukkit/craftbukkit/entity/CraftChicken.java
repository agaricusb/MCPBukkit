package org.bukkit.craftbukkit.entity;


import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;

public class CraftChicken extends CraftAnimals implements Chicken {

    public CraftChicken(CraftServer server, net.minecraft.entity.passive.EntityChicken/*was:EntityChicken*/ entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.entity.passive.EntityChicken/*was:EntityChicken*/ getHandle() {
        return (net.minecraft.entity.passive.EntityChicken/*was:EntityChicken*/) entity;
    }

    @Override
    public String toString() {
        return "CraftChicken";
    }

    public EntityType getType() {
        return EntityType.CHICKEN;
    }
}
