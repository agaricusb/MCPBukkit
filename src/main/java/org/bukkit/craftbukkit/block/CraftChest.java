package org.bukkit.craftbukkit.block;

import org.bukkit.Location;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.craftbukkit.inventory.CraftInventoryDoubleChest;
import org.bukkit.inventory.Inventory;

public class CraftChest extends CraftBlockState implements Chest {
    private final CraftWorld world;
    private final net.minecraft.tileentity.TileEntityChest/*was:TileEntityChest*/ chest;

    public CraftChest(final Block block) {
        super(block);

        world = (CraftWorld) block.getWorld();
        chest = (net.minecraft.tileentity.TileEntityChest/*was:TileEntityChest*/) world.getTileEntityAt(getX(), getY(), getZ());
    }

    public Inventory getBlockInventory() {
        return new CraftInventory(chest);
    }

    public Inventory getInventory() {
        int x = getX();
        int y = getY();
        int z = getZ();
        // The logic here is basically identical to the logic in BlockChest.interact
        CraftInventory inventory = new CraftInventory(chest);
        if (world.getBlockTypeIdAt(x - 1, y, z) == Material.CHEST.getId()) {
            CraftInventory left = new CraftInventory((net.minecraft.tileentity.TileEntityChest/*was:TileEntityChest*/)world.getHandle().func_72796_p/*was:getTileEntity*/(x - 1, y, z));
            inventory = new CraftInventoryDoubleChest(left, inventory);
        }
        if (world.getBlockTypeIdAt(x + 1, y, z) == Material.CHEST.getId()) {
            CraftInventory right = new CraftInventory((net.minecraft.tileentity.TileEntityChest/*was:TileEntityChest*/) world.getHandle().func_72796_p/*was:getTileEntity*/(x + 1, y, z));
            inventory = new CraftInventoryDoubleChest(inventory, right);
        }
        if (world.getBlockTypeIdAt(x, y, z - 1) == Material.CHEST.getId()) {
            CraftInventory left = new CraftInventory((net.minecraft.tileentity.TileEntityChest/*was:TileEntityChest*/) world.getHandle().func_72796_p/*was:getTileEntity*/(x, y, z - 1));
            inventory = new CraftInventoryDoubleChest(left, inventory);
        }
        if (world.getBlockTypeIdAt(x, y, z + 1) == Material.CHEST.getId()) {
            CraftInventory right = new CraftInventory((net.minecraft.tileentity.TileEntityChest/*was:TileEntityChest*/) world.getHandle().func_72796_p/*was:getTileEntity*/(x, y, z + 1));
            inventory = new CraftInventoryDoubleChest(inventory, right);
        }
        return inventory;
    }

    @Override
    public boolean update(boolean force) {
        boolean result = super.update(force);

        if (result) {
            chest.func_70296_d/*was:update*/();
        }

        return result;
    }
}
