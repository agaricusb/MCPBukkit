package org.bukkit.craftbukkit.entity;


import org.bukkit.Material;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.bukkit.material.MaterialData;

public class CraftEnderman extends CraftMonster implements Enderman {
    public CraftEnderman(CraftServer server, net.minecraft.entity.monster.EntityEnderman entity) {
        super(server, entity);
    }

    public MaterialData getCarriedMaterial() {
        return Material.getMaterial(getHandle().func_70822_p()).getNewData((byte) getHandle().func_70824_q());
    }

    public void setCarriedMaterial(MaterialData data) {
        getHandle().func_70818_a(data.getItemTypeId());
        getHandle().func_70817_b(data.getData());
    }

    @Override
    public net.minecraft.entity.monster.EntityEnderman getHandle() {
        return (net.minecraft.entity.monster.EntityEnderman) entity;
    }

    @Override
    public String toString() {
        return "CraftEnderman";
    }

    public EntityType getType() {
        return EntityType.ENDERMAN;
    }
}
