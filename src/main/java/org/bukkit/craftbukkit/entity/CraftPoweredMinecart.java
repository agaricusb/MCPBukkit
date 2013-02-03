package org.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.entity.CraftMinecart;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.PoweredMinecart;

public class CraftPoweredMinecart extends CraftMinecart implements PoweredMinecart {
    public CraftPoweredMinecart(CraftServer server, net.minecraft.entity.item.EntityMinecart/*was:EntityMinecart*/ entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftPoweredMinecart";
    }
}