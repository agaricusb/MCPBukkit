package org.bukkit.craftbukkit.entity;


import org.bukkit.DyeColor;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Sheep;

public class CraftSheep extends CraftAnimals implements Sheep {
    public CraftSheep(CraftServer server, net.minecraft.entity.passive.EntitySheep/*was:EntitySheep*/ entity) {
        super(server, entity);
    }

    public DyeColor getColor() {
        return DyeColor.getByWoolData((byte) getHandle().func_70896_n/*was:getColor*/());
    }

    public void setColor(DyeColor color) {
        getHandle().func_70891_b/*was:setColor*/(color.getWoolData());
    }

    public boolean isSheared() {
        return getHandle().func_70892_o/*was:isSheared*/();
    }

    public void setSheared(boolean flag) {
        getHandle().func_70893_e/*was:setSheared*/(flag);
    }

    @Override
    public net.minecraft.entity.passive.EntitySheep/*was:EntitySheep*/ getHandle() {
        return (net.minecraft.entity.passive.EntitySheep/*was:EntitySheep*/) entity;
    }

    @Override
    public String toString() {
        return "CraftSheep";
    }

    public EntityType getType() {
        return EntityType.SHEEP;
    }
}
