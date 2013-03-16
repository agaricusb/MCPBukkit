package net.minecraft.tileentity;

import java.util.Random;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

// CraftBukkit start
import java.util.List;

import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
// CraftBukkit end

public class TileEntityDispenser extends TileEntity implements IInventory
{
    private ItemStack[] field_70363_a = new ItemStack[9];
    private Random field_70362_b = new Random();
    protected String field_94050_c;

    // CraftBukkit start
    public List<HumanEntity> transaction = new java.util.ArrayList<HumanEntity>();
    private int maxStack = MAX_STACK;

    public ItemStack[] getContents()
    {
        return this.field_70363_a;
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

    public void setMaxStackSize(int size)
    {
        maxStack = size;
    }
    // CraftBukkit end

    public TileEntityDispenser() {}

    public int func_70302_i_()
    {
        return 9;
    }

    public ItemStack func_70301_a(int p_70301_1_)
    {
        return this.field_70363_a[p_70301_1_];
    }

    public ItemStack func_70298_a(int p_70298_1_, int p_70298_2_)
    {
        if (this.field_70363_a[p_70298_1_] != null)
        {
            ItemStack itemstack;

            if (this.field_70363_a[p_70298_1_].field_77994_a <= p_70298_2_)
            {
                itemstack = this.field_70363_a[p_70298_1_];
                this.field_70363_a[p_70298_1_] = null;
                this.func_70296_d();
                return itemstack;
            }
            else
            {
                itemstack = this.field_70363_a[p_70298_1_].func_77979_a(p_70298_2_);

                if (this.field_70363_a[p_70298_1_].field_77994_a == 0)
                {
                    this.field_70363_a[p_70298_1_] = null;
                }

                this.func_70296_d();
                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    public ItemStack func_70304_b(int p_70304_1_)
    {
        if (this.field_70363_a[p_70304_1_] != null)
        {
            ItemStack itemstack = this.field_70363_a[p_70304_1_];
            this.field_70363_a[p_70304_1_] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    public int func_70361_i()
    {
        int i = -1;
        int j = 1;

        for (int k = 0; k < this.field_70363_a.length; ++k)
        {
            if (this.field_70363_a[k] != null && this.field_70362_b.nextInt(j++) == 0)
            {
                if (this.field_70363_a[k].field_77994_a == 0)
                {
                    continue;    // CraftBukkit
                }

                i = k;
            }
        }

        return i;
    }

    public void func_70299_a(int p_70299_1_, ItemStack p_70299_2_)
    {
        this.field_70363_a[p_70299_1_] = p_70299_2_;

        if (p_70299_2_ != null && p_70299_2_.field_77994_a > this.func_70297_j_())
        {
            p_70299_2_.field_77994_a = this.func_70297_j_();
        }

        this.func_70296_d();
    }

    public int func_70360_a(ItemStack p_70360_1_)
    {
        for (int i = 0; i < this.field_70363_a.length; ++i)
        {
            if (this.field_70363_a[i] == null || this.field_70363_a[i].field_77993_c == 0)
            {
                this.func_70299_a(i, p_70360_1_);
                return i;
            }
        }

        return -1;
    }

    public String func_70303_b()
    {
        return this.func_94042_c() ? this.field_94050_c : "container.dispenser";
    }

    public void func_94049_a(String p_94049_1_)
    {
        this.field_94050_c = p_94049_1_;
    }

    public boolean func_94042_c()
    {
        return this.field_94050_c != null;
    }

    public void func_70307_a(NBTTagCompound p_70307_1_)
    {
        super.func_70307_a(p_70307_1_);
        NBTTagList nbttaglist = p_70307_1_.func_74761_m("Items");
        this.field_70363_a = new ItemStack[this.func_70302_i_()];

        for (int i = 0; i < nbttaglist.func_74745_c(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.func_74743_b(i);
            int j = nbttagcompound1.func_74771_c("Slot") & 255;

            if (j >= 0 && j < this.field_70363_a.length)
            {
                this.field_70363_a[j] = ItemStack.func_77949_a(nbttagcompound1);
            }
        }

        if (p_70307_1_.func_74764_b("CustomName"))
        {
            this.field_94050_c = p_70307_1_.func_74779_i("CustomName");
        }
    }

    public void func_70310_b(NBTTagCompound p_70310_1_)
    {
        super.func_70310_b(p_70310_1_);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.field_70363_a.length; ++i)
        {
            if (this.field_70363_a[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.func_74774_a("Slot", (byte)i);
                this.field_70363_a[i].func_77955_b(nbttagcompound1);
                nbttaglist.func_74742_a(nbttagcompound1);
            }
        }

        p_70310_1_.func_74782_a("Items", nbttaglist);

        if (this.func_94042_c())
        {
            p_70310_1_.func_74778_a("CustomName", this.field_94050_c);
        }
    }

    public int func_70297_j_()
    {
        return maxStack; // CraftBukkit
    }

    public boolean func_70300_a(EntityPlayer p_70300_1_)
    {
        return this.field_70331_k.func_72796_p(this.field_70329_l, this.field_70330_m, this.field_70327_n) != this ? false : p_70300_1_.func_70092_e((double)this.field_70329_l + 0.5D, (double)this.field_70330_m + 0.5D, (double)this.field_70327_n + 0.5D) <= 64.0D;
    }

    public void func_70295_k_() {}

    public void func_70305_f() {}

    public boolean func_94041_b(int p_94041_1_, ItemStack p_94041_2_)
    {
        return true;
    }
}
