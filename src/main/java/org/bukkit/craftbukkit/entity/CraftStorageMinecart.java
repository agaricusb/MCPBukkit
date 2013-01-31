package org.bukkit.craftbukkit.entity;


import org.bukkit.inventory.Inventory;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.StorageMinecart;

public class CraftStorageMinecart extends CraftMinecart implements StorageMinecart {
    private final CraftInventory inventory;

    public CraftStorageMinecart(CraftServer server, net.minecraft.entity.item.EntityMinecart/*was:EntityMinecart*/ entity) {
        super(server, entity);
        inventory = new CraftInventory(entity);
    }

    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public String toString() {
        return "CraftStorageMinecart{" + "inventory=" + inventory + '}';
    }
}
