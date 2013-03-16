package net.minecraft.tileentity;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionHelper;

// CraftBukkit start
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.BrewEvent;
// CraftBukkit end

public class TileEntityBrewingStand extends TileEntity implements ISidedInventory
{
    public ItemStack[] field_70359_a = new ItemStack[4]; // CraftBukkit private -> public
    public int field_70357_b; // CraftBukkit private -> public
    private int field_70358_c;
    private int field_70356_d;
    private String field_94132_e;

    public TileEntityBrewingStand() {}

    // CraftBukkit start
    public List<HumanEntity> transaction = new java.util.ArrayList<HumanEntity>();
    private int maxStack = 64;

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

    public ItemStack[] getContents()
    {
        return this.field_70359_a;
    }

    public void setMaxStackSize(int size)
    {
        maxStack = size;
    }
    // CraftBukkit end

    public String func_70303_b()
    {
        return this.func_94042_c() ? this.field_94132_e : "container.brewing";
    }

    public boolean func_94042_c()
    {
        return this.field_94132_e != null && this.field_94132_e.length() > 0;
    }

    public void func_94131_a(String p_94131_1_)
    {
        this.field_94132_e = p_94131_1_;
    }

    public int func_70302_i_()
    {
        return this.field_70359_a.length;
    }

    public void func_70316_g()
    {
        if (this.field_70357_b > 0)
        {
            --this.field_70357_b;

            if (this.field_70357_b == 0)
            {
                this.func_70353_r();
                this.func_70296_d();
            }
            else if (!this.func_70350_k())
            {
                this.field_70357_b = 0;
                this.func_70296_d();
            }
            else if (this.field_70356_d != this.field_70359_a[3].field_77993_c)
            {
                this.field_70357_b = 0;
                this.func_70296_d();
            }
        }
        else if (this.func_70350_k())
        {
            this.field_70357_b = 400;
            this.field_70356_d = this.field_70359_a[3].field_77993_c;
        }

        int i = this.func_70351_i();

        if (i != this.field_70358_c)
        {
            this.field_70358_c = i;
            this.field_70331_k.func_72921_c(this.field_70329_l, this.field_70330_m, this.field_70327_n, i, 2);
        }

        super.func_70316_g();
    }

    public int func_70355_t_()
    {
        return this.field_70357_b;
    }

    private boolean func_70350_k()
    {
        if (this.field_70359_a[3] != null && this.field_70359_a[3].field_77994_a > 0)
        {
            ItemStack itemstack = this.field_70359_a[3];

            if (!Item.field_77698_e[itemstack.field_77993_c].func_77632_u())
            {
                return false;
            }
            else
            {
                boolean flag = false;

                for (int i = 0; i < 3; ++i)
                {
                    if (this.field_70359_a[i] != null && this.field_70359_a[i].field_77993_c == Item.field_77726_bs.field_77779_bT)
                    {
                        int j = this.field_70359_a[i].func_77960_j();
                        int k = this.func_70352_b(j, itemstack);

                        if (!ItemPotion.func_77831_g(j) && ItemPotion.func_77831_g(k))
                        {
                            flag = true;
                            break;
                        }

                        List list = Item.field_77726_bs.func_77834_f(j);
                        List list1 = Item.field_77726_bs.func_77834_f(k);

                        if ((j <= 0 || list != list1) && (list == null || !list.equals(list1) && list1 != null) && j != k)
                        {
                            flag = true;
                            break;
                        }
                    }
                }

                return flag;
            }
        }
        else
        {
            return false;
        }
    }

    private void func_70353_r()
    {
        if (this.func_70350_k())
        {
            ItemStack itemstack = this.field_70359_a[3];

            // CraftBukkit start - fire BREW event
            if (getOwner() != null)
            {
                BrewEvent event = new BrewEvent(field_70331_k.getWorld().getBlockAt(field_70329_l, field_70330_m, field_70327_n), (org.bukkit.inventory.BrewerInventory) this.getOwner().getInventory());
                org.bukkit.Bukkit.getPluginManager().callEvent(event);

                if (event.isCancelled())
                {
                    return;
                }
            }

            // CraftBukkit end

            for (int i = 0; i < 3; ++i)
            {
                if (this.field_70359_a[i] != null && this.field_70359_a[i].field_77993_c == Item.field_77726_bs.field_77779_bT)
                {
                    int j = this.field_70359_a[i].func_77960_j();
                    int k = this.func_70352_b(j, itemstack);
                    List list = Item.field_77726_bs.func_77834_f(j);
                    List list1 = Item.field_77726_bs.func_77834_f(k);

                    if ((j <= 0 || list != list1) && (list == null || !list.equals(list1) && list1 != null))
                    {
                        if (j != k)
                        {
                            this.field_70359_a[i].func_77964_b(k);
                        }
                    }
                    else if (!ItemPotion.func_77831_g(j) && ItemPotion.func_77831_g(k))
                    {
                        this.field_70359_a[i].func_77964_b(k);
                    }
                }
            }

            if (Item.field_77698_e[itemstack.field_77993_c].func_77634_r())
            {
                this.field_70359_a[3] = new ItemStack(Item.field_77698_e[itemstack.field_77993_c].func_77668_q());
            }
            else
            {
                --this.field_70359_a[3].field_77994_a;

                if (this.field_70359_a[3].field_77994_a <= 0)
                {
                    this.field_70359_a[3] = null;
                }
            }
        }
    }

    private int func_70352_b(int p_70352_1_, ItemStack p_70352_2_)
    {
        return p_70352_2_ == null ? p_70352_1_ : (Item.field_77698_e[p_70352_2_.field_77993_c].func_77632_u() ? PotionHelper.func_77913_a(p_70352_1_, Item.field_77698_e[p_70352_2_.field_77993_c].func_77666_t()) : p_70352_1_);
    }

    public void func_70307_a(NBTTagCompound p_70307_1_)
    {
        super.func_70307_a(p_70307_1_);
        NBTTagList nbttaglist = p_70307_1_.func_74761_m("Items");
        this.field_70359_a = new ItemStack[this.func_70302_i_()];

        for (int i = 0; i < nbttaglist.func_74745_c(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.func_74743_b(i);
            byte b0 = nbttagcompound1.func_74771_c("Slot");

            if (b0 >= 0 && b0 < this.field_70359_a.length)
            {
                this.field_70359_a[b0] = ItemStack.func_77949_a(nbttagcompound1);
            }
        }

        this.field_70357_b = p_70307_1_.func_74765_d("BrewTime");

        if (p_70307_1_.func_74764_b("CustomName"))
        {
            this.field_94132_e = p_70307_1_.func_74779_i("CustomName");
        }
    }

    public void func_70310_b(NBTTagCompound p_70310_1_)
    {
        super.func_70310_b(p_70310_1_);
        p_70310_1_.func_74777_a("BrewTime", (short)this.field_70357_b);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.field_70359_a.length; ++i)
        {
            if (this.field_70359_a[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.func_74774_a("Slot", (byte)i);
                this.field_70359_a[i].func_77955_b(nbttagcompound1);
                nbttaglist.func_74742_a(nbttagcompound1);
            }
        }

        p_70310_1_.func_74782_a("Items", nbttaglist);

        if (this.func_94042_c())
        {
            p_70310_1_.func_74778_a("CustomName", this.field_94132_e);
        }
    }

    public ItemStack func_70301_a(int p_70301_1_)
    {
        return p_70301_1_ >= 0 && p_70301_1_ < this.field_70359_a.length ? this.field_70359_a[p_70301_1_] : null;
    }

    public ItemStack func_70298_a(int p_70298_1_, int p_70298_2_)
    {
        if (p_70298_1_ >= 0 && p_70298_1_ < this.field_70359_a.length)
        {
            ItemStack itemstack = this.field_70359_a[p_70298_1_];
            this.field_70359_a[p_70298_1_] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    public ItemStack func_70304_b(int p_70304_1_)
    {
        if (p_70304_1_ >= 0 && p_70304_1_ < this.field_70359_a.length)
        {
            ItemStack itemstack = this.field_70359_a[p_70304_1_];
            this.field_70359_a[p_70304_1_] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    public void func_70299_a(int p_70299_1_, ItemStack p_70299_2_)
    {
        if (p_70299_1_ >= 0 && p_70299_1_ < this.field_70359_a.length)
        {
            this.field_70359_a[p_70299_1_] = p_70299_2_;
        }
    }

    public int func_70297_j_()
    {
        return this.maxStack; // CraftBukkit
    }

    public boolean func_70300_a(EntityPlayer p_70300_1_)
    {
        return this.field_70331_k.func_72796_p(this.field_70329_l, this.field_70330_m, this.field_70327_n) != this ? false : p_70300_1_.func_70092_e((double)this.field_70329_l + 0.5D, (double)this.field_70330_m + 0.5D, (double)this.field_70327_n + 0.5D) <= 64.0D;
    }

    public void func_70295_k_() {}

    public void func_70305_f() {}

    public boolean func_94041_b(int p_94041_1_, ItemStack p_94041_2_)
    {
        return p_94041_1_ == 3 ? Item.field_77698_e[p_94041_2_.field_77993_c].func_77632_u() : p_94041_2_.field_77993_c == Item.field_77726_bs.field_77779_bT || p_94041_2_.field_77993_c == Item.field_77729_bt.field_77779_bT;
    }

    public int func_70351_i()
    {
        int i = 0;

        for (int j = 0; j < 3; ++j)
        {
            if (this.field_70359_a[j] != null)
            {
                i |= 1 << j;
            }
        }

        return i;
    }

    public int func_94127_c(int p_94127_1_)
    {
        return p_94127_1_ == 1 ? 3 : 0;
    }

    public int func_94128_d(int p_94128_1_)
    {
        return p_94128_1_ == 1 ? 1 : 3;
    }
}
