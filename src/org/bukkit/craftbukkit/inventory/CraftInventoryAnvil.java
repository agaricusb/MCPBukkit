package org.bukkit.craftbukkit.inventory;

import org.bukkit.inventory.AnvilInventory;

public class CraftInventoryAnvil extends CraftInventory implements AnvilInventory {
    public CraftInventoryAnvil(net.minecraft.inventory.IInventory anvil) {
        super(anvil);
    }
}
