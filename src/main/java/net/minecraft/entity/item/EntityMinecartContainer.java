package net.minecraft.entity.item;

// CraftBukkit start
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
// CraftBukkit end

public abstract class EntityMinecartContainer extends EntityMinecart implements IInventory
{
    private ItemStack[] field_94113_a = new ItemStack[36];
    private boolean field_94112_b = true;

    // CraftBukkit start
    public List<HumanEntity> transaction = new java.util.ArrayList<HumanEntity>();
    private int maxStack = MAX_STACK;

    public ItemStack[] getContents()
    {
        return this.field_94113_a;
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

    public EntityMinecartContainer(World p_i10051_1_)
    {
        super(p_i10051_1_);
    }

    public EntityMinecartContainer(World p_i10052_1_, double p_i10052_2_, double p_i10052_4_, double p_i10052_6_)
    {
        super(p_i10052_1_, p_i10052_2_, p_i10052_4_, p_i10052_6_);
    }

    public void func_94095_a(DamageSource p_94095_1_)
    {
        super.func_94095_a(p_94095_1_);

        for (int i = 0; i < this.func_70302_i_(); ++i)
        {
            ItemStack itemstack = this.func_70301_a(i);

            if (itemstack != null)
            {
                float f = this.field_70146_Z.nextFloat() * 0.8F + 0.1F;
                float f1 = this.field_70146_Z.nextFloat() * 0.8F + 0.1F;
                float f2 = this.field_70146_Z.nextFloat() * 0.8F + 0.1F;

                while (itemstack.field_77994_a > 0)
                {
                    int j = this.field_70146_Z.nextInt(21) + 10;

                    if (j > itemstack.field_77994_a)
                    {
                        j = itemstack.field_77994_a;
                    }

                    itemstack.field_77994_a -= j;
                    EntityItem entityitem = new EntityItem(this.field_70170_p, this.field_70165_t + (double)f, this.field_70163_u + (double)f1, this.field_70161_v + (double)f2, new ItemStack(itemstack.field_77993_c, j, itemstack.func_77960_j()));
                    float f3 = 0.05F;
                    entityitem.field_70159_w = (double)((float)this.field_70146_Z.nextGaussian() * f3);
                    entityitem.field_70181_x = (double)((float)this.field_70146_Z.nextGaussian() * f3 + 0.2F);
                    entityitem.field_70179_y = (double)((float)this.field_70146_Z.nextGaussian() * f3);
                    this.field_70170_p.func_72838_d(entityitem);
                }
            }
        }
    }

    public ItemStack func_70301_a(int p_70301_1_)
    {
        return this.field_94113_a[p_70301_1_];
    }

    public ItemStack func_70298_a(int p_70298_1_, int p_70298_2_)
    {
        if (this.field_94113_a[p_70298_1_] != null)
        {
            ItemStack itemstack;

            if (this.field_94113_a[p_70298_1_].field_77994_a <= p_70298_2_)
            {
                itemstack = this.field_94113_a[p_70298_1_];
                this.field_94113_a[p_70298_1_] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.field_94113_a[p_70298_1_].func_77979_a(p_70298_2_);

                if (this.field_94113_a[p_70298_1_].field_77994_a == 0)
                {
                    this.field_94113_a[p_70298_1_] = null;
                }

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
        if (this.field_94113_a[p_70304_1_] != null)
        {
            ItemStack itemstack = this.field_94113_a[p_70304_1_];
            this.field_94113_a[p_70304_1_] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    public void func_70299_a(int p_70299_1_, ItemStack p_70299_2_)
    {
        this.field_94113_a[p_70299_1_] = p_70299_2_;

        if (p_70299_2_ != null && p_70299_2_.field_77994_a > this.func_70297_j_())
        {
            p_70299_2_.field_77994_a = this.func_70297_j_();
        }
    }

    public void func_70296_d() {}

    public boolean func_70300_a(EntityPlayer p_70300_1_)
    {
        return this.field_70128_L ? false : p_70300_1_.func_70068_e(this) <= 64.0D;
    }

    public void func_70295_k_() {}

    public void func_70305_f() {}

    public boolean func_94041_b(int p_94041_1_, ItemStack p_94041_2_)
    {
        return true;
    }

    public String func_70303_b()
    {
        return this.func_94042_c() ? this.func_95999_t() : "container.minecart";
    }

    public int func_70297_j_()
    {
        return 64;
    }

    public void func_71027_c(int p_71027_1_)
    {
        this.field_94112_b = false;
        super.func_71027_c(p_71027_1_);
    }

    public void func_70106_y()
    {
        if (this.field_94112_b)
        {
            for (int i = 0; i < this.func_70302_i_(); ++i)
            {
                ItemStack itemstack = this.func_70301_a(i);

                if (itemstack != null)
                {
                    float f = this.field_70146_Z.nextFloat() * 0.8F + 0.1F;
                    float f1 = this.field_70146_Z.nextFloat() * 0.8F + 0.1F;
                    float f2 = this.field_70146_Z.nextFloat() * 0.8F + 0.1F;

                    while (itemstack.field_77994_a > 0)
                    {
                        int j = this.field_70146_Z.nextInt(21) + 10;

                        if (j > itemstack.field_77994_a)
                        {
                            j = itemstack.field_77994_a;
                        }

                        itemstack.field_77994_a -= j;
                        EntityItem entityitem = new EntityItem(this.field_70170_p, this.field_70165_t + (double)f, this.field_70163_u + (double)f1, this.field_70161_v + (double)f2, new ItemStack(itemstack.field_77993_c, j, itemstack.func_77960_j()));

                        if (itemstack.func_77942_o())
                        {
                            entityitem.func_92059_d().func_77982_d((NBTTagCompound)itemstack.func_77978_p().func_74737_b());
                        }

                        float f3 = 0.05F;
                        entityitem.field_70159_w = (double)((float)this.field_70146_Z.nextGaussian() * f3);
                        entityitem.field_70181_x = (double)((float)this.field_70146_Z.nextGaussian() * f3 + 0.2F);
                        entityitem.field_70179_y = (double)((float)this.field_70146_Z.nextGaussian() * f3);
                        this.field_70170_p.func_72838_d(entityitem);
                    }
                }
            }
        }

        super.func_70106_y();
    }

    protected void func_70014_b(NBTTagCompound p_70014_1_)
    {
        super.func_70014_b(p_70014_1_);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.field_94113_a.length; ++i)
        {
            if (this.field_94113_a[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.func_74774_a("Slot", (byte)i);
                this.field_94113_a[i].func_77955_b(nbttagcompound1);
                nbttaglist.func_74742_a(nbttagcompound1);
            }
        }

        p_70014_1_.func_74782_a("Items", nbttaglist);
    }

    protected void func_70037_a(NBTTagCompound p_70037_1_)
    {
        super.func_70037_a(p_70037_1_);
        NBTTagList nbttaglist = p_70037_1_.func_74761_m("Items");
        this.field_94113_a = new ItemStack[this.func_70302_i_()];

        for (int i = 0; i < nbttaglist.func_74745_c(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.func_74743_b(i);
            int j = nbttagcompound1.func_74771_c("Slot") & 255;

            if (j >= 0 && j < this.field_94113_a.length)
            {
                this.field_94113_a[j] = ItemStack.func_77949_a(nbttagcompound1);
            }
        }
    }

    public boolean func_70085_c(EntityPlayer p_70085_1_)
    {
        if (!this.field_70170_p.field_72995_K)
        {
            p_70085_1_.func_71007_a(this);
        }

        return true;
    }

    protected void func_94101_h()
    {
        int i = 15 - Container.func_94526_b((IInventory) this);
        float f = 0.98F + (float)i * 0.001F;
        this.field_70159_w *= (double)f;
        this.field_70181_x *= 0.0D;
        this.field_70179_y *= (double)f;
    }
}
