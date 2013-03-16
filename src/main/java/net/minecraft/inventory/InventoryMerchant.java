package net.minecraft.inventory;

// CraftBukkit start
import java.util.List;
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
// CraftBukkit end

public class InventoryMerchant implements IInventory
{
    private final IMerchant field_70476_a;
    private ItemStack[] field_70474_b = new ItemStack[3];
    private final EntityPlayer field_70475_c;
    private MerchantRecipe field_70472_d;
    private int field_70473_e;

    // CraftBukkit start
    public List<HumanEntity> transaction = new java.util.ArrayList<HumanEntity>();
    private int maxStack = MAX_STACK;

    public ItemStack[] getContents()
    {
        return this.field_70474_b;
    }

    public void onOpen(CraftHumanEntity who)
    {
        transaction.add(who);
    }

    public void onClose(CraftHumanEntity who)
    {
        transaction.remove(who);
    }

    public List<HumanEntity> getViewers()
    {
        return transaction;
    }

    public void setMaxStackSize(int i)
    {
        maxStack = i;
    }

    public org.bukkit.inventory.InventoryHolder getOwner()
    {
        return field_70475_c.getBukkitEntity();
    }
    // CraftBukkit end

    public InventoryMerchant(EntityPlayer p_i3612_1_, IMerchant p_i3612_2_)
    {
        this.field_70475_c = p_i3612_1_;
        this.field_70476_a = p_i3612_2_;
    }

    public int func_70302_i_()
    {
        return this.field_70474_b.length;
    }

    public ItemStack func_70301_a(int p_70301_1_)
    {
        return this.field_70474_b[p_70301_1_];
    }

    public ItemStack func_70298_a(int p_70298_1_, int p_70298_2_)
    {
        if (this.field_70474_b[p_70298_1_] != null)
        {
            ItemStack itemstack;

            if (p_70298_1_ == 2)
            {
                itemstack = this.field_70474_b[p_70298_1_];
                this.field_70474_b[p_70298_1_] = null;
                return itemstack;
            }
            else if (this.field_70474_b[p_70298_1_].field_77994_a <= p_70298_2_)
            {
                itemstack = this.field_70474_b[p_70298_1_];
                this.field_70474_b[p_70298_1_] = null;

                if (this.func_70469_d(p_70298_1_))
                {
                    this.func_70470_g();
                }

                return itemstack;
            }
            else
            {
                itemstack = this.field_70474_b[p_70298_1_].func_77979_a(p_70298_2_);

                if (this.field_70474_b[p_70298_1_].field_77994_a == 0)
                {
                    this.field_70474_b[p_70298_1_] = null;
                }

                if (this.func_70469_d(p_70298_1_))
                {
                    this.func_70470_g();
                }

                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    private boolean func_70469_d(int p_70469_1_)
    {
        return p_70469_1_ == 0 || p_70469_1_ == 1;
    }

    public ItemStack func_70304_b(int p_70304_1_)
    {
        if (this.field_70474_b[p_70304_1_] != null)
        {
            ItemStack itemstack = this.field_70474_b[p_70304_1_];
            this.field_70474_b[p_70304_1_] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    public void func_70299_a(int p_70299_1_, ItemStack p_70299_2_)
    {
        this.field_70474_b[p_70299_1_] = p_70299_2_;

        if (p_70299_2_ != null && p_70299_2_.field_77994_a > this.func_70297_j_())
        {
            p_70299_2_.field_77994_a = this.func_70297_j_();
        }

        if (this.func_70469_d(p_70299_1_))
        {
            this.func_70470_g();
        }
    }

    public String func_70303_b()
    {
        return "mob.villager";
    }

    public boolean func_94042_c()
    {
        return false;
    }

    public int func_70297_j_()
    {
        return maxStack; // CraftBukkit
    }

    public boolean func_70300_a(EntityPlayer p_70300_1_)
    {
        return this.field_70476_a.func_70931_l_() == p_70300_1_;
    }

    public void func_70295_k_() {}

    public void func_70305_f() {}

    public boolean func_94041_b(int p_94041_1_, ItemStack p_94041_2_)
    {
        return true;
    }

    public void func_70296_d()
    {
        this.func_70470_g();
    }

    public void func_70470_g()
    {
        this.field_70472_d = null;
        ItemStack itemstack = this.field_70474_b[0];
        ItemStack itemstack1 = this.field_70474_b[1];

        if (itemstack == null)
        {
            itemstack = itemstack1;
            itemstack1 = null;
        }

        if (itemstack == null)
        {
            this.func_70299_a(2, (ItemStack)null);
        }
        else
        {
            MerchantRecipeList merchantrecipelist = this.field_70476_a.func_70934_b(this.field_70475_c);

            if (merchantrecipelist != null)
            {
                MerchantRecipe merchantrecipe = merchantrecipelist.func_77203_a(itemstack, itemstack1, this.field_70473_e);

                if (merchantrecipe != null && !merchantrecipe.func_82784_g())
                {
                    this.field_70472_d = merchantrecipe;
                    this.func_70299_a(2, merchantrecipe.func_77397_d().func_77946_l());
                }
                else if (itemstack1 != null)
                {
                    merchantrecipe = merchantrecipelist.func_77203_a(itemstack1, itemstack, this.field_70473_e);

                    if (merchantrecipe != null && !merchantrecipe.func_82784_g())
                    {
                        this.field_70472_d = merchantrecipe;
                        this.func_70299_a(2, merchantrecipe.func_77397_d().func_77946_l());
                    }
                    else
                    {
                        this.func_70299_a(2, (ItemStack)null);
                    }
                }
                else
                {
                    this.func_70299_a(2, (ItemStack)null);
                }
            }
        }
    }

    public MerchantRecipe func_70468_h()
    {
        return this.field_70472_d;
    }

    public void func_70471_c(int p_70471_1_)
    {
        this.field_70473_e = p_70471_1_;
        this.func_70470_g();
    }
}
