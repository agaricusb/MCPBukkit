package net.minecraft.tileentity;

import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;

// CraftBukkit start
import java.util.List;

import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
// CraftBukkit end

public class TileEntityBeacon extends TileEntity implements IInventory
{
    public static final Potion[][] field_82139_a = new Potion[][] {{Potion.field_76424_c, Potion.field_76422_e}, {Potion.field_76429_m, Potion.field_76430_j}, {Potion.field_76420_g}, {Potion.field_76428_l}};
    private boolean field_82135_d;
    private int field_82136_e = -1;
    private int field_82133_f;
    private int field_82134_g;
    private ItemStack field_82140_h;
    // CraftBukkit start
    public List<HumanEntity> transaction = new java.util.ArrayList<HumanEntity>();
    private int maxStack = MAX_STACK;

    public ItemStack[] getContents()
    {
        return null;
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

    public TileEntityBeacon() {}

    public void func_70316_g()
    {
        if (this.field_70331_k.func_82737_E() % 80L == 0L)
        {
            this.func_82131_u();
            this.func_82124_t();
        }
    }

    private void func_82124_t()
    {
        if (this.field_82135_d && this.field_82136_e > 0 && !this.field_70331_k.field_72995_K && this.field_82133_f > 0)
        {
            double d0 = (double)(this.field_82136_e * 8 + 8);
            byte b0 = 0;

            if (this.field_82136_e >= 4 && this.field_82133_f == this.field_82134_g)
            {
                b0 = 1;
            }

            AxisAlignedBB axisalignedbb = AxisAlignedBB.func_72332_a().func_72299_a((double)this.field_70329_l, (double)this.field_70330_m, (double)this.field_70327_n, (double)(this.field_70329_l + 1), (double)(this.field_70330_m + 1), (double)(this.field_70327_n + 1)).func_72314_b(d0, d0, d0);
            List list = this.field_70331_k.func_72872_a(EntityPlayer.class, axisalignedbb);
            Iterator iterator = list.iterator();
            EntityPlayer entityplayer;

            while (iterator.hasNext())
            {
                entityplayer = (EntityPlayer)iterator.next();
                entityplayer.func_70690_d(new PotionEffect(this.field_82133_f, 180, b0, true));
            }

            if (this.field_82136_e >= 4 && this.field_82133_f != this.field_82134_g && this.field_82134_g > 0)
            {
                iterator = list.iterator();

                while (iterator.hasNext())
                {
                    entityplayer = (EntityPlayer)iterator.next();
                    entityplayer.func_70690_d(new PotionEffect(this.field_82134_g, 180, 0, true));
                }
            }
        }
    }

    private void func_82131_u()
    {
        if (!this.field_70331_k.func_72937_j(this.field_70329_l, this.field_70330_m + 1, this.field_70327_n))
        {
            this.field_82135_d = false;
            this.field_82136_e = 0;
        }
        else
        {
            this.field_82135_d = true;
            this.field_82136_e = 0;

            for (int i = 1; i <= 4; this.field_82136_e = i++)
            {
                int j = this.field_70330_m - i;

                if (j < 1)
                {
                    break;
                }

                boolean flag = true;

                for (int k = this.field_70329_l - i; k <= this.field_70329_l + i && flag; ++k)
                {
                    for (int l = this.field_70327_n - i; l <= this.field_70327_n + i; ++l)
                    {
                        int i1 = this.field_70331_k.func_72798_a(k, j, l);

                        if (i1 != Block.field_72076_bV.field_71990_ca && i1 != Block.field_72105_ah.field_71990_ca && i1 != Block.field_72071_ax.field_71990_ca && i1 != Block.field_72083_ai.field_71990_ca)
                        {
                            flag = false;
                            break;
                        }
                    }
                }

                if (!flag)
                {
                    break;
                }
            }

            if (this.field_82136_e == 0)
            {
                this.field_82135_d = false;
            }
        }
    }

    public int func_82126_i()
    {
        return this.field_82133_f;
    }

    public int func_82132_j()
    {
        return this.field_82134_g;
    }

    public int func_82130_k()
    {
        return this.field_82136_e;
    }

    public void func_82128_d(int p_82128_1_)
    {
        this.field_82133_f = 0;

        for (int j = 0; j < this.field_82136_e && j < 3; ++j)
        {
            Potion[] apotion = field_82139_a[j];
            int k = apotion.length;

            for (int l = 0; l < k; ++l)
            {
                Potion potion = apotion[l];

                if (potion.field_76415_H == p_82128_1_)
                {
                    this.field_82133_f = p_82128_1_;
                    return;
                }
            }
        }
    }

    public void func_82127_e(int p_82127_1_)
    {
        this.field_82134_g = 0;

        if (this.field_82136_e >= 4)
        {
            for (int j = 0; j < 4; ++j)
            {
                Potion[] apotion = field_82139_a[j];
                int k = apotion.length;

                for (int l = 0; l < k; ++l)
                {
                    Potion potion = apotion[l];

                    if (potion.field_76415_H == p_82127_1_)
                    {
                        this.field_82134_g = p_82127_1_;
                        return;
                    }
                }
            }
        }
    }

    public Packet func_70319_e()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.func_70310_b(nbttagcompound);
        return new Packet132TileEntityData(this.field_70329_l, this.field_70330_m, this.field_70327_n, 3, nbttagcompound);
    }

    public void func_70307_a(NBTTagCompound p_70307_1_)
    {
        super.func_70307_a(p_70307_1_);
        this.field_82133_f = p_70307_1_.func_74762_e("Primary");
        this.field_82134_g = p_70307_1_.func_74762_e("Secondary");
        this.field_82136_e = p_70307_1_.func_74762_e("Levels");
    }

    public void func_70310_b(NBTTagCompound p_70310_1_)
    {
        super.func_70310_b(p_70310_1_);
        p_70310_1_.func_74768_a("Primary", this.field_82133_f);
        p_70310_1_.func_74768_a("Secondary", this.field_82134_g);
        p_70310_1_.func_74768_a("Levels", this.field_82136_e);
    }

    public int func_70302_i_()
    {
        return 1;
    }

    public ItemStack func_70301_a(int p_70301_1_)
    {
        return p_70301_1_ == 0 ? this.field_82140_h : null;
    }

    public ItemStack func_70298_a(int p_70298_1_, int p_70298_2_)
    {
        if (p_70298_1_ == 0 && this.field_82140_h != null)
        {
            if (p_70298_2_ >= this.field_82140_h.field_77994_a)
            {
                ItemStack itemstack = this.field_82140_h;
                this.field_82140_h = null;
                return itemstack;
            }
            else
            {
                this.field_82140_h.field_77994_a -= p_70298_2_;
                return new ItemStack(this.field_82140_h.field_77993_c, p_70298_2_, this.field_82140_h.func_77960_j());
            }
        }
        else
        {
            return null;
        }
    }

    public ItemStack func_70304_b(int p_70304_1_)
    {
        if (p_70304_1_ == 0 && this.field_82140_h != null)
        {
            ItemStack itemstack = this.field_82140_h;
            this.field_82140_h = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    public void func_70299_a(int p_70299_1_, ItemStack p_70299_2_)
    {
        if (p_70299_1_ == 0)
        {
            this.field_82140_h = p_70299_2_;
        }
    }

    public String func_70303_b()
    {
        return "container.beacon";
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
}
