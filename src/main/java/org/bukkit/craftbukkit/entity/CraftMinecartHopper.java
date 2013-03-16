package org.bukkit.craftbukkit.entity;


import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.minecart.HopperMinecart;

final class CraftMinecartHopper extends CraftMinecart implements HopperMinecart {
    CraftMinecartHopper(CraftServer server, net.minecraft.entity.item.EntityMinecartHopper entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftMinecartHopper";
    }

    public EntityType getType() {
        return EntityType.MINECART_HOPPER;
    }
}
