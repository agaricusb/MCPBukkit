package org.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.MinecartTNT;

final class CraftMinecartTNT extends CraftMinecart implements MinecartTNT {
    CraftMinecartTNT(CraftServer server, net.minecraft.entity.item.EntityMinecartTNT entity) {
        super(server, entity);
    }
}
