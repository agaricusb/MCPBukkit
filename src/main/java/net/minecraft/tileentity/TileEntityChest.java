package net.minecraft.tileentity;

import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;

// CraftBukkit start
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
// CraftBukkit end

public class TileEntityChest extends TileEntity implements IInventory
{
    private ItemStack[] field_70428_i = new ItemStack[27]; // CraftBukkit - 36 -> 27
    public boolean field_70425_a = false;
    public TileEntityChest field_70423_b;
    public TileEntityChest field_70424_c;
    public TileEntityChest field_70421_d;
    public TileEntityChest field_70422_e;
    public float field_70419_f;
    public float field_70420_g;
    public int field_70427_h;
    private int field_70426_j;
    private int field_94046_i = -1;
    private String field_94045_s;

    public TileEntityChest() {}

    // CraftBukkit start
    public List<HumanEntity> transaction = new java.util.ArrayList<HumanEntity>();
    private int maxStack = MAX_STACK;

    public ItemStack[] getContents()
    {
        return this.field_70428_i;
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

    public int func_70302_i_()
    {
        return 27;
    }

    public ItemStack func_70301_a(int p_70301_1_)
    {
        return this.field_70428_i[p_70301_1_];
    }

    public ItemStack func_70298_a(int p_70298_1_, int p_70298_2_)
    {
        if (this.field_70428_i[p_70298_1_] != null)
        {
            ItemStack itemstack;

            if (this.field_70428_i[p_70298_1_].field_77994_a <= p_70298_2_)
            {
                itemstack = this.field_70428_i[p_70298_1_];
                this.field_70428_i[p_70298_1_] = null;
                this.func_70296_d();
                return itemstack;
            }
            else
            {
                itemstack = this.field_70428_i[p_70298_1_].func_77979_a(p_70298_2_);

                if (this.field_70428_i[p_70298_1_].field_77994_a == 0)
                {
                    this.field_70428_i[p_70298_1_] = null;
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
        if (this.field_70428_i[p_70304_1_] != null)
        {
            ItemStack itemstack = this.field_70428_i[p_70304_1_];
            this.field_70428_i[p_70304_1_] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    public void func_70299_a(int p_70299_1_, ItemStack p_70299_2_)
    {
        this.field_70428_i[p_70299_1_] = p_70299_2_;

        if (p_70299_2_ != null && p_70299_2_.field_77994_a > this.func_70297_j_())
        {
            p_70299_2_.field_77994_a = this.func_70297_j_();
        }

        this.func_70296_d();
    }

    public String func_70303_b()
    {
        return this.func_94042_c() ? this.field_94045_s : "container.chest";
    }

    public boolean func_94042_c()
    {
        return this.field_94045_s != null && this.field_94045_s.length() > 0;
    }

    public void func_94043_a(String p_94043_1_)
    {
        this.field_94045_s = p_94043_1_;
    }

    public void func_70307_a(NBTTagCompound p_70307_1_)
    {
        super.func_70307_a(p_70307_1_);
        NBTTagList nbttaglist = p_70307_1_.func_74761_m("Items");
        this.field_70428_i = new ItemStack[this.func_70302_i_()];

        if (p_70307_1_.func_74764_b("CustomName"))
        {
            this.field_94045_s = p_70307_1_.func_74779_i("CustomName");
        }

        for (int i = 0; i < nbttaglist.func_74745_c(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.func_74743_b(i);
            int j = nbttagcompound1.func_74771_c("Slot") & 255;

            if (j >= 0 && j < this.field_70428_i.length)
            {
                this.field_70428_i[j] = ItemStack.func_77949_a(nbttagcompound1);
            }
        }
    }

    public void func_70310_b(NBTTagCompound p_70310_1_)
    {
        super.func_70310_b(p_70310_1_);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.field_70428_i.length; ++i)
        {
            if (this.field_70428_i[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.func_74774_a("Slot", (byte)i);
                this.field_70428_i[i].func_77955_b(nbttagcompound1);
                nbttaglist.func_74742_a(nbttagcompound1);
            }
        }

        p_70310_1_.func_74782_a("Items", nbttaglist);

        if (this.func_94042_c())
        {
            p_70310_1_.func_74778_a("CustomName", this.field_94045_s);
        }
    }

    public int func_70297_j_()
    {
        return maxStack; // CraftBukkit
    }

    public boolean func_70300_a(EntityPlayer p_70300_1_)
    {
        if (this.field_70331_k == null)
        {
            return true;    // CraftBukkit
        }

        return this.field_70331_k.func_72796_p(this.field_70329_l, this.field_70330_m, this.field_70327_n) != this ? false : p_70300_1_.func_70092_e((double)this.field_70329_l + 0.5D, (double)this.field_70330_m + 0.5D, (double)this.field_70327_n + 0.5D) <= 64.0D;
    }

    public void func_70321_h()
    {
        super.func_70321_h();
        this.field_70425_a = false;
    }

    private void func_90009_a(TileEntityChest p_90009_1_, int p_90009_2_)
    {
        if (p_90009_1_.func_70320_p())
        {
            this.field_70425_a = false;
        }
        else if (this.field_70425_a)
        {
            switch (p_90009_2_)
            {
                case 0:
                    if (this.field_70422_e != p_90009_1_)
                    {
                        this.field_70425_a = false;
                    }

                    break;

                case 1:
                    if (this.field_70421_d != p_90009_1_)
                    {
                        this.field_70425_a = false;
                    }

                    break;

                case 2:
                    if (this.field_70423_b != p_90009_1_)
                    {
                        this.field_70425_a = false;
                    }

                    break;

                case 3:
                    if (this.field_70424_c != p_90009_1_)
                    {
                        this.field_70425_a = false;
                    }
            }
        }
    }

    public void func_70418_i()
    {
        if (!this.field_70425_a)
        {
            this.field_70425_a = true;
            this.field_70423_b = null;
            this.field_70424_c = null;
            this.field_70421_d = null;
            this.field_70422_e = null;

            if (this.func_94044_a(this.field_70329_l - 1, this.field_70330_m, this.field_70327_n))
            {
                this.field_70421_d = (TileEntityChest)this.field_70331_k.func_72796_p(this.field_70329_l - 1, this.field_70330_m, this.field_70327_n);
            }

            if (this.func_94044_a(this.field_70329_l + 1, this.field_70330_m, this.field_70327_n))
            {
                this.field_70424_c = (TileEntityChest)this.field_70331_k.func_72796_p(this.field_70329_l + 1, this.field_70330_m, this.field_70327_n);
            }

            if (this.func_94044_a(this.field_70329_l, this.field_70330_m, this.field_70327_n - 1))
            {
                this.field_70423_b = (TileEntityChest)this.field_70331_k.func_72796_p(this.field_70329_l, this.field_70330_m, this.field_70327_n - 1);
            }

            if (this.func_94044_a(this.field_70329_l, this.field_70330_m, this.field_70327_n + 1))
            {
                this.field_70422_e = (TileEntityChest)this.field_70331_k.func_72796_p(this.field_70329_l, this.field_70330_m, this.field_70327_n + 1);
            }

            if (this.field_70423_b != null)
            {
                this.field_70423_b.func_90009_a(this, 0);
            }

            if (this.field_70422_e != null)
            {
                this.field_70422_e.func_90009_a(this, 2);
            }

            if (this.field_70424_c != null)
            {
                this.field_70424_c.func_90009_a(this, 1);
            }

            if (this.field_70421_d != null)
            {
                this.field_70421_d.func_90009_a(this, 3);
            }
        }
    }

    private boolean func_94044_a(int p_94044_1_, int p_94044_2_, int p_94044_3_)
    {
        Block block = Block.field_71973_m[this.field_70331_k.func_72798_a(p_94044_1_, p_94044_2_, p_94044_3_)];
        return block != null && block instanceof BlockChest ? ((BlockChest)block).field_94443_a == this.func_98041_l() : false;
    }

    public void func_70316_g()
    {
        super.func_70316_g();

        if (this.field_70331_k == null)
        {
            return;    // CraftBukkit
        }

        this.func_70418_i();
        ++this.field_70426_j;
        float f;

        if (!this.field_70331_k.field_72995_K && this.field_70427_h != 0 && (this.field_70426_j + this.field_70329_l + this.field_70330_m + this.field_70327_n) % 200 == 0)
        {
            this.field_70427_h = 0;
            f = 5.0F;
            List list = this.field_70331_k.func_72872_a(EntityPlayer.class, AxisAlignedBB.func_72332_a().func_72299_a((double)((float)this.field_70329_l - f), (double)((float)this.field_70330_m - f), (double)((float)this.field_70327_n - f), (double)((float)(this.field_70329_l + 1) + f), (double)((float)(this.field_70330_m + 1) + f), (double)((float)(this.field_70327_n + 1) + f)));
            Iterator iterator = list.iterator();

            while (iterator.hasNext())
            {
                EntityPlayer entityplayer = (EntityPlayer)iterator.next();

                if (entityplayer.field_71070_bA instanceof ContainerChest)
                {
                    IInventory iinventory = ((ContainerChest)entityplayer.field_71070_bA).func_85151_d();

                    if (iinventory == this || iinventory instanceof InventoryLargeChest && ((InventoryLargeChest) iinventory).func_90010_a((IInventory) this))
                    {
                        ++this.field_70427_h;
                    }
                }
            }
        }

        this.field_70420_g = this.field_70419_f;
        f = 0.1F;
        double d0;

        if (this.field_70427_h > 0 && this.field_70419_f == 0.0F && this.field_70423_b == null && this.field_70421_d == null)
        {
            double d1 = (double)this.field_70329_l + 0.5D;
            d0 = (double)this.field_70327_n + 0.5D;

            if (this.field_70422_e != null)
            {
                d0 += 0.5D;
            }

            if (this.field_70424_c != null)
            {
                d1 += 0.5D;
            }

            this.field_70331_k.func_72908_a(d1, (double)this.field_70330_m + 0.5D, d0, "random.chestopen", 0.5F, this.field_70331_k.field_73012_v.nextFloat() * 0.1F + 0.9F);
        }

        if (this.field_70427_h == 0 && this.field_70419_f > 0.0F || this.field_70427_h > 0 && this.field_70419_f < 1.0F)
        {
            float f1 = this.field_70419_f;

            if (this.field_70427_h > 0)
            {
                this.field_70419_f += f;
            }
            else
            {
                this.field_70419_f -= f;
            }

            if (this.field_70419_f > 1.0F)
            {
                this.field_70419_f = 1.0F;
            }

            float f2 = 0.5F;

            if (this.field_70419_f < f2 && f1 >= f2 && this.field_70423_b == null && this.field_70421_d == null)
            {
                d0 = (double)this.field_70329_l + 0.5D;
                double d2 = (double)this.field_70327_n + 0.5D;

                if (this.field_70422_e != null)
                {
                    d2 += 0.5D;
                }

                if (this.field_70424_c != null)
                {
                    d0 += 0.5D;
                }

                this.field_70331_k.func_72908_a(d0, (double)this.field_70330_m + 0.5D, d2, "random.chestclosed", 0.5F, this.field_70331_k.field_73012_v.nextFloat() * 0.1F + 0.9F);
            }

            if (this.field_70419_f < 0.0F)
            {
                this.field_70419_f = 0.0F;
            }
        }
    }

    public boolean func_70315_b(int p_70315_1_, int p_70315_2_)
    {
        if (p_70315_1_ == 1)
        {
            this.field_70427_h = p_70315_2_;
            return true;
        }
        else
        {
            return super.func_70315_b(p_70315_1_, p_70315_2_);
        }
    }

    public void func_70295_k_()
    {
        if (this.field_70427_h < 0)
        {
            this.field_70427_h = 0;
        }

        ++this.field_70427_h;

        if (this.field_70331_k == null)
        {
            return;    // CraftBukkit
        }

        this.field_70331_k.func_72965_b(this.field_70329_l, this.field_70330_m, this.field_70327_n, this.func_70311_o().field_71990_ca, 1, this.field_70427_h);
        this.field_70331_k.func_72898_h(this.field_70329_l, this.field_70330_m, this.field_70327_n, this.func_70311_o().field_71990_ca);
        this.field_70331_k.func_72898_h(this.field_70329_l, this.field_70330_m - 1, this.field_70327_n, this.func_70311_o().field_71990_ca);
    }

    public void func_70305_f()
    {
        if (this.func_70311_o() != null && this.func_70311_o() instanceof BlockChest)
        {
            --this.field_70427_h;

            if (this.field_70331_k == null)
            {
                return;    // CraftBukkit
            }

            this.field_70331_k.func_72965_b(this.field_70329_l, this.field_70330_m, this.field_70327_n, this.func_70311_o().field_71990_ca, 1, this.field_70427_h);
            this.field_70331_k.func_72898_h(this.field_70329_l, this.field_70330_m, this.field_70327_n, this.func_70311_o().field_71990_ca);
            this.field_70331_k.func_72898_h(this.field_70329_l, this.field_70330_m - 1, this.field_70327_n, this.func_70311_o().field_71990_ca);
        }
    }

    public boolean func_94041_b(int p_94041_1_, ItemStack p_94041_2_)
    {
        return true;
    }

    public void func_70313_j()
    {
        super.func_70313_j();
        this.func_70321_h();
        this.func_70418_i();
    }

    public int func_98041_l()
    {
        if (this.field_94046_i == -1)
        {
            if (this.field_70331_k == null || !(this.func_70311_o() instanceof BlockChest))
            {
                return 0;
            }

            this.field_94046_i = ((BlockChest)this.func_70311_o()).field_94443_a;
        }

        return this.field_94046_i;
    }
}
