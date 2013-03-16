package net.minecraft.item.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
public class RecipesMapCloning extends ShapelessRecipes implements IRecipe   // CraftBukkit - added extends
{
    // CraftBukkit start - delegate to new parent class
    public RecipesMapCloning()
    {
        super(new ItemStack(Item.field_77744_bd, 0, -1), java.util.Arrays.asList(new ItemStack(Item.field_82801_bO, 0, 0)));
    }
    // CraftBukkit end

    public boolean func_77569_a(InventoryCrafting p_77569_1_, World p_77569_2_)
    {
        int i = 0;
        ItemStack itemstack = null;

        for (int j = 0; j < p_77569_1_.func_70302_i_(); ++j)
        {
            ItemStack itemstack1 = p_77569_1_.func_70301_a(j);

            if (itemstack1 != null)
            {
                if (itemstack1.field_77993_c == Item.field_77744_bd.field_77779_bT)
                {
                    if (itemstack != null)
                    {
                        return false;
                    }

                    itemstack = itemstack1;
                }
                else
                {
                    if (itemstack1.field_77993_c != Item.field_82801_bO.field_77779_bT)
                    {
                        return false;
                    }

                    ++i;
                }
            }
        }

        return itemstack != null && i > 0;
    }

    public ItemStack func_77572_b(InventoryCrafting p_77572_1_)
    {
        int i = 0;
        ItemStack itemstack = null;

        for (int j = 0; j < p_77572_1_.func_70302_i_(); ++j)
        {
            ItemStack itemstack1 = p_77572_1_.func_70301_a(j);

            if (itemstack1 != null)
            {
                if (itemstack1.field_77993_c == Item.field_77744_bd.field_77779_bT)
                {
                    if (itemstack != null)
                    {
                        return null;
                    }

                    itemstack = itemstack1;
                }
                else
                {
                    if (itemstack1.field_77993_c != Item.field_82801_bO.field_77779_bT)
                    {
                        return null;
                    }

                    ++i;
                }
            }
        }

        if (itemstack != null && i >= 1)
        {
            ItemStack itemstack2 = new ItemStack(Item.field_77744_bd, i + 1, itemstack.func_77960_j());

            if (itemstack.func_82837_s())
            {
                itemstack2.func_82834_c(itemstack.func_82833_r());
            }

            return itemstack2;
        }
        else
        {
            return null;
        }
    }

    public int func_77570_a()
    {
        return 9;
    }

    public ItemStack func_77571_b()
    {
        return null;
    }
}
