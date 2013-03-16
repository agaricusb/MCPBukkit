package org.bukkit.craftbukkit.entity;


import org.bukkit.DyeColor;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Sheep;

public class CraftSheep extends CraftAnimals implements Sheep {
    public CraftSheep(CraftServer server, net.minecraft.entity.passive.EntitySheep entity) {
        super(server, entity);
    }

    public DyeColor getColor() {
        return DyeColor.getByWoolData((byte) getHandle().func_70896_n());
    }

    public void setColor(DyeColor color) {
        getHandle().func_70891_b(color.getWoolData());
    }

    public boolean isSheared() {
        return getHandle().func_70892_o();
    }

    public void setSheared(boolean flag) {
        getHandle().func_70893_e(flag);
    }

    @Override
    public net.minecraft.entity.passive.EntitySheep getHandle() {
        return (net.minecraft.entity.passive.EntitySheep) entity;
    }

    @Override
    public String toString() {
        return "CraftSheep";
    }

    public EntityType getType() {
        return EntityType.SHEEP;
    }
}
