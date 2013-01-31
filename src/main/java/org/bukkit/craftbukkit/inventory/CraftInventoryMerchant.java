package org.bukkit.craftbukkit.inventory;

import org.bukkit.inventory.MerchantInventory;

public class CraftInventoryMerchant extends CraftInventory implements MerchantInventory {
    public CraftInventoryMerchant(net.minecraft.inventory.InventoryMerchant/*was:InventoryMerchant*/ merchant) {
        super(merchant);
    }
}
