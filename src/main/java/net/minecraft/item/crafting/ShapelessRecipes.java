package net.minecraft.item.crafting;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

// CraftBukkit start
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.craftbukkit.inventory.CraftShapelessRecipe;
// CraftBukkit end

public class ShapelessRecipes implements IRecipe
{
    private final ItemStack field_77580_a;
    private final List field_77579_b;

    public ShapelessRecipes(ItemStack p_i3701_1_, List p_i3701_2_)
    {
        this.field_77580_a = p_i3701_1_;
        this.field_77579_b = p_i3701_2_;
    }

    // CraftBukkit start
    @SuppressWarnings("unchecked")
    public org.bukkit.inventory.ShapelessRecipe toBukkitRecipe()
    {
        CraftItemStack result = CraftItemStack.asCraftMirror(this.field_77580_a);
        CraftShapelessRecipe recipe = new CraftShapelessRecipe(result, this);

        for (ItemStack stack : (List<ItemStack>) this.field_77579_b)
        {
            if (stack != null)
            {
                recipe.addIngredient(org.bukkit.Material.getMaterial(stack.field_77993_c), stack.func_77960_j());
            }
        }

        return recipe;
    }
    // CraftBukkit end

    public ItemStack func_77571_b()
    {
        return this.field_77580_a;
    }

    public boolean func_77569_a(InventoryCrafting p_77569_1_, World p_77569_2_)
    {
        ArrayList arraylist = new ArrayList(this.field_77579_b);

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 3; ++j)
            {
                ItemStack itemstack = p_77569_1_.func_70463_b(j, i);

                if (itemstack != null)
                {
                    boolean flag = false;
                    Iterator iterator = arraylist.iterator();

                    while (iterator.hasNext())
                    {
                        ItemStack itemstack1 = (ItemStack)iterator.next();

                        if (itemstack.field_77993_c == itemstack1.field_77993_c && (itemstack1.func_77960_j() == -1 || itemstack.func_77960_j() == itemstack1.func_77960_j()))
                        {
                            flag = true;
                            arraylist.remove(itemstack1);
                            break;
                        }
                    }

                    if (!flag)
                    {
                        return false;
                    }
                }
            }
        }

        return arraylist.isEmpty();
    }

    public ItemStack func_77572_b(InventoryCrafting p_77572_1_)
    {
        return this.field_77580_a.func_77946_l();
    }

    public int func_77570_a()
    {
        return this.field_77579_b.size();
    }
}
