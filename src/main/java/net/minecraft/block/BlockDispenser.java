package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.IRegistry;
import net.minecraft.dispenser.PositionImpl;
import net.minecraft.dispenser.RegistryDefaulted;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockDispenser extends BlockContainer
{
    public static final IRegistry field_82527_a = new RegistryDefaulted(new BehaviorDefaultDispenseItem());
    protected Random field_72284_a = new Random();
    public static boolean eventFired = false; // CraftBukkit

    protected BlockDispenser(int p_i3938_1_)
    {
        super(p_i3938_1_, Material.field_76246_e);
        this.func_71849_a(CreativeTabs.field_78028_d);
    }

    public int func_71859_p_(World p_71859_1_)
    {
        return 4;
    }

    public void func_71861_g(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
    {
        super.func_71861_g(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_);
        this.func_72280_l(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_);
    }

    private void func_72280_l(World p_72280_1_, int p_72280_2_, int p_72280_3_, int p_72280_4_)
    {
        if (!p_72280_1_.field_72995_K)
        {
            int l = p_72280_1_.func_72798_a(p_72280_2_, p_72280_3_, p_72280_4_ - 1);
            int i1 = p_72280_1_.func_72798_a(p_72280_2_, p_72280_3_, p_72280_4_ + 1);
            int j1 = p_72280_1_.func_72798_a(p_72280_2_ - 1, p_72280_3_, p_72280_4_);
            int k1 = p_72280_1_.func_72798_a(p_72280_2_ + 1, p_72280_3_, p_72280_4_);
            byte b0 = 3;

            if (Block.field_71970_n[l] && !Block.field_71970_n[i1])
            {
                b0 = 3;
            }

            if (Block.field_71970_n[i1] && !Block.field_71970_n[l])
            {
                b0 = 2;
            }

            if (Block.field_71970_n[j1] && !Block.field_71970_n[k1])
            {
                b0 = 5;
            }

            if (Block.field_71970_n[k1] && !Block.field_71970_n[j1])
            {
                b0 = 4;
            }

            p_72280_1_.func_72921_c(p_72280_2_, p_72280_3_, p_72280_4_, b0, 2);
        }
    }

    public boolean func_71903_a(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
    {
        if (p_71903_1_.field_72995_K)
        {
            return true;
        }
        else
        {
            TileEntityDispenser tileentitydispenser = (TileEntityDispenser)p_71903_1_.func_72796_p(p_71903_2_, p_71903_3_, p_71903_4_);

            if (tileentitydispenser != null)
            {
                p_71903_5_.func_71006_a(tileentitydispenser);
            }

            return true;
        }
    }

    // CraftBukkit - private -> public
    public void func_82526_n(World p_82526_1_, int p_82526_2_, int p_82526_3_, int p_82526_4_)
    {
        BlockSourceImpl blocksourceimpl = new BlockSourceImpl(p_82526_1_, p_82526_2_, p_82526_3_, p_82526_4_);
        TileEntityDispenser tileentitydispenser = (TileEntityDispenser)blocksourceimpl.func_82619_j();

        if (tileentitydispenser != null)
        {
            int l = tileentitydispenser.func_70361_i();

            if (l < 0)
            {
                p_82526_1_.func_72926_e(1001, p_82526_2_, p_82526_3_, p_82526_4_, 0);
            }
            else
            {
                ItemStack itemstack = tileentitydispenser.func_70301_a(l);
                IBehaviorDispenseItem ibehaviordispenseitem = this.func_96472_a(itemstack);

                if (ibehaviordispenseitem != IBehaviorDispenseItem.field_82483_a)
                {
                    ItemStack itemstack1 = ibehaviordispenseitem.func_82482_a(blocksourceimpl, itemstack);
                    eventFired = false; // CraftBukkit - reset event status
                    tileentitydispenser.func_70299_a(l, itemstack1.field_77994_a == 0 ? null : itemstack1);
                }
            }
        }
    }

    protected IBehaviorDispenseItem func_96472_a(ItemStack p_96472_1_)
    {
        return (IBehaviorDispenseItem)field_82527_a.func_82594_a(p_96472_1_.func_77973_b());
    }

    public void func_71863_a(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
    {
        boolean flag = p_71863_1_.func_72864_z(p_71863_2_, p_71863_3_, p_71863_4_) || p_71863_1_.func_72864_z(p_71863_2_, p_71863_3_ + 1, p_71863_4_);
        int i1 = p_71863_1_.func_72805_g(p_71863_2_, p_71863_3_, p_71863_4_);
        boolean flag1 = (i1 & 8) != 0;

        if (flag && !flag1)
        {
            p_71863_1_.func_72836_a(p_71863_2_, p_71863_3_, p_71863_4_, this.field_71990_ca, this.func_71859_p_(p_71863_1_));
            p_71863_1_.func_72921_c(p_71863_2_, p_71863_3_, p_71863_4_, i1 | 8, 4);
        }
        else if (!flag && flag1)
        {
            p_71863_1_.func_72921_c(p_71863_2_, p_71863_3_, p_71863_4_, i1 & -9, 4);
        }
    }

    public void func_71847_b(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
    {
        if (!p_71847_1_.field_72995_K)
        {
            this.func_82526_n(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_);
        }
    }

    public TileEntity func_72274_a(World p_72274_1_)
    {
        return new TileEntityDispenser();
    }

    public void func_71860_a(World p_71860_1_, int p_71860_2_, int p_71860_3_, int p_71860_4_, EntityLiving p_71860_5_, ItemStack p_71860_6_)
    {
        int l = BlockPistonBase.func_72116_b(p_71860_1_, p_71860_2_, p_71860_3_, p_71860_4_, p_71860_5_);
        p_71860_1_.func_72921_c(p_71860_2_, p_71860_3_, p_71860_4_, l, 2);

        if (p_71860_6_.func_82837_s())
        {
            ((TileEntityDispenser)p_71860_1_.func_72796_p(p_71860_2_, p_71860_3_, p_71860_4_)).func_94049_a(p_71860_6_.func_82833_r());
        }
    }

    public void func_71852_a(World p_71852_1_, int p_71852_2_, int p_71852_3_, int p_71852_4_, int p_71852_5_, int p_71852_6_)
    {
        TileEntityDispenser tileentitydispenser = (TileEntityDispenser)p_71852_1_.func_72796_p(p_71852_2_, p_71852_3_, p_71852_4_);

        if (tileentitydispenser != null)
        {
            for (int j1 = 0; j1 < tileentitydispenser.func_70302_i_(); ++j1)
            {
                ItemStack itemstack = tileentitydispenser.func_70301_a(j1);

                if (itemstack != null)
                {
                    float f = this.field_72284_a.nextFloat() * 0.8F + 0.1F;
                    float f1 = this.field_72284_a.nextFloat() * 0.8F + 0.1F;
                    float f2 = this.field_72284_a.nextFloat() * 0.8F + 0.1F;

                    while (itemstack.field_77994_a > 0)
                    {
                        int k1 = this.field_72284_a.nextInt(21) + 10;

                        if (k1 > itemstack.field_77994_a)
                        {
                            k1 = itemstack.field_77994_a;
                        }

                        itemstack.field_77994_a -= k1;
                        EntityItem entityitem = new EntityItem(p_71852_1_, (double)((float)p_71852_2_ + f), (double)((float)p_71852_3_ + f1), (double)((float)p_71852_4_ + f2), new ItemStack(itemstack.field_77993_c, k1, itemstack.func_77960_j()));

                        if (itemstack.func_77942_o())
                        {
                            entityitem.func_92059_d().func_77982_d((NBTTagCompound)itemstack.func_77978_p().func_74737_b());
                        }

                        float f3 = 0.05F;
                        entityitem.field_70159_w = (double)((float)this.field_72284_a.nextGaussian() * f3);
                        entityitem.field_70181_x = (double)((float)this.field_72284_a.nextGaussian() * f3 + 0.2F);
                        entityitem.field_70179_y = (double)((float)this.field_72284_a.nextGaussian() * f3);
                        p_71852_1_.func_72838_d(entityitem);
                    }
                }
            }
        }

        super.func_71852_a(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_, p_71852_5_, p_71852_6_);
    }

    public static IPosition func_82525_a(IBlockSource p_82525_0_)
    {
        EnumFacing enumfacing = func_100009_j_(p_82525_0_.func_82620_h());
        double d0 = p_82525_0_.func_82615_a() + 0.7D * (double)enumfacing.func_82601_c();
        double d1 = p_82525_0_.func_82617_b() + 0.7D * (double)enumfacing.func_96559_d();
        double d2 = p_82525_0_.func_82616_c() + 0.7D * (double)enumfacing.func_82599_e();
        return new PositionImpl(d0, d1, d2);
    }

    public static EnumFacing func_100009_j_(int p_100009_0_)
    {
        return EnumFacing.func_82600_a(p_100009_0_ & 7);
    }

    public boolean func_96468_q_()
    {
        return true;
    }

    public int func_94328_b_(World p_94328_1_, int p_94328_2_, int p_94328_3_, int p_94328_4_, int p_94328_5_)
    {
        return Container.func_94526_b((IInventory)p_94328_1_.func_72796_p(p_94328_2_, p_94328_3_, p_94328_4_));
    }
}
