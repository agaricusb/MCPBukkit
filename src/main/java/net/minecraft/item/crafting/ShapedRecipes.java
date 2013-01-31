package net.minecraft.item.crafting;

// CraftBukkit start
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.craftbukkit.inventory.CraftShapedRecipe;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
// CraftBukkit end

public class ShapedRecipes implements IRecipe
{
    private int field_77576_b;
    private int field_77577_c;
    private ItemStack[] field_77574_d;
    private ItemStack field_77575_e;
    public final int field_77578_a;
    private boolean field_92101_f = false;

    public ShapedRecipes(int p_i3700_1_, int p_i3700_2_, ItemStack[] p_i3700_3_, ItemStack p_i3700_4_)
    {
        this.field_77578_a = p_i3700_4_.field_77993_c;
        this.field_77576_b = p_i3700_1_;
        this.field_77577_c = p_i3700_2_;
        this.field_77574_d = p_i3700_3_;
        this.field_77575_e = p_i3700_4_;
    }

    // CraftBukkit start
    public org.bukkit.inventory.ShapedRecipe toBukkitRecipe()
    {
        CraftItemStack result = CraftItemStack.asCraftMirror(this.field_77575_e);
        CraftShapedRecipe recipe = new CraftShapedRecipe(result, this);

        switch (this.field_77577_c)
        {
            case 1:
                switch (this.field_77576_b)
                {
                    case 1:
                        recipe.shape("a");
                        break;
                    case 2:
                        recipe.shape("ab");
                        break;
                    case 3:
                        recipe.shape("abc");
                        break;
                }

                break;
            case 2:
                switch (this.field_77576_b)
                {
                    case 1:
                        recipe.shape("a", "b");
                        break;
                    case 2:
                        recipe.shape("ab", "cd");
                        break;
                    case 3:
                        recipe.shape("abc", "def");
                        break;
                }

                break;
            case 3:
                switch (this.field_77576_b)
                {
                    case 1:
                        recipe.shape("a", "b", "c");
                        break;
                    case 2:
                        recipe.shape("ab", "cd", "ef");
                        break;
                    case 3:
                        recipe.shape("abc", "def", "ghi");
                        break;
                }

                break;
        }

        char c = 'a';

        for (ItemStack stack : this.field_77574_d)
        {
            if (stack != null)
            {
                recipe.setIngredient(c, org.bukkit.Material.getMaterial(stack.field_77993_c), stack.func_77960_j());
            }

            c++;
        }

        return recipe;
    }
    // CraftBukkit end

    public ItemStack func_77571_b()
    {
        return this.field_77575_e;
    }

    public boolean func_77569_a(InventoryCrafting p_77569_1_, World p_77569_2_)
    {
        for (int i = 0; i <= 3 - this.field_77576_b; ++i)
        {
            for (int j = 0; j <= 3 - this.field_77577_c; ++j)
            {
                if (this.func_77573_a(p_77569_1_, i, j, true))
                {
                    return true;
                }

                if (this.func_77573_a(p_77569_1_, i, j, false))
                {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean func_77573_a(InventoryCrafting p_77573_1_, int p_77573_2_, int p_77573_3_, boolean p_77573_4_)
    {
        for (int k = 0; k < 3; ++k)
        {
            for (int l = 0; l < 3; ++l)
            {
                int i1 = k - p_77573_2_;
                int j1 = l - p_77573_3_;
                ItemStack itemstack = null;

                if (i1 >= 0 && j1 >= 0 && i1 < this.field_77576_b && j1 < this.field_77577_c)
                {
                    if (p_77573_4_)
                    {
                        itemstack = this.field_77574_d[this.field_77576_b - i1 - 1 + j1 * this.field_77576_b];
                    }
                    else
                    {
                        itemstack = this.field_77574_d[i1 + j1 * this.field_77576_b];
                    }
                }

                ItemStack itemstack1 = p_77573_1_.func_70463_b(k, l);

                if (itemstack1 != null || itemstack != null)
                {
                    if (itemstack1 == null && itemstack != null || itemstack1 != null && itemstack == null)
                    {
                        return false;
                    }

                    if (itemstack.field_77993_c != itemstack1.field_77993_c)
                    {
                        return false;
                    }

                    if (itemstack.func_77960_j() != -1 && itemstack.func_77960_j() != itemstack1.func_77960_j())
                    {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public ItemStack func_77572_b(InventoryCrafting p_77572_1_)
    {
        ItemStack itemstack = this.func_77571_b().func_77946_l();

        if (this.field_92101_f)
        {
            for (int i = 0; i < p_77572_1_.func_70302_i_(); ++i)
            {
                ItemStack itemstack1 = p_77572_1_.func_70301_a(i);

                if (itemstack1 != null && itemstack1.func_77942_o())
                {
                    itemstack.func_77982_d((NBTTagCompound)itemstack1.field_77990_d.func_74737_b());
                }
            }
        }

        return itemstack;
    }

    public int func_77570_a()
    {
        return this.field_77576_b * this.field_77577_c;
    }

    public ShapedRecipes func_92100_c()
    {
        this.field_92101_f = true;
        return this;
    }
}
