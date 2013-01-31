package net.minecraft.item.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
public interface IRecipe
{
    boolean func_77569_a(InventoryCrafting inventorycrafting, World world);

    ItemStack func_77572_b(InventoryCrafting inventorycrafting);

    int func_77570_a();

    ItemStack func_77571_b();

    org.bukkit.inventory.Recipe toBukkitRecipe(); // CraftBukkit
}
