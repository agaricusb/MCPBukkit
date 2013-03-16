package net.minecraft.inventory;

import org.bukkit.craftbukkit.entity.CraftHumanEntity; // CraftBukkit
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IInventory
{
    int func_70302_i_();

    ItemStack func_70301_a(int i);

    ItemStack func_70298_a(int i, int j);

    ItemStack func_70304_b(int i);

    void func_70299_a(int i, ItemStack itemstack);

    String func_70303_b();

    boolean func_94042_c();

    int func_70297_j_();

    void func_70296_d();

    boolean func_70300_a(EntityPlayer entityhuman);

    void func_70295_k_();

    void func_70305_f();

    boolean func_94041_b(int i, ItemStack itemstack);

    // CraftBukkit start
    ItemStack[] getContents();

    void onOpen(CraftHumanEntity who);

    void onClose(CraftHumanEntity who);

    java.util.List<org.bukkit.entity.HumanEntity> getViewers();

    org.bukkit.inventory.InventoryHolder getOwner();

    void setMaxStackSize(int size);

    int MAX_STACK = 64;
    // CraftBukkit end
}
