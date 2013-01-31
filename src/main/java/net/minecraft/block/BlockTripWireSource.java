package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Direction;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.bukkit.event.block.BlockRedstoneEvent; // CraftBukkit

public class BlockTripWireSource extends Block
{
    public BlockTripWireSource(int p_i4017_1_)
    {
        super(p_i4017_1_, 172, Material.field_76265_p);
        this.func_71849_a(CreativeTabs.field_78028_d);
        this.func_71907_b(true);
    }

    public AxisAlignedBB func_71872_e(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
    {
        return null;
    }

    public boolean func_71926_d()
    {
        return false;
    }

    public boolean func_71886_c()
    {
        return false;
    }

    public int func_71857_b()
    {
        return 29;
    }

    public int func_71859_p_()
    {
        return 10;
    }

    public boolean func_71850_a_(World p_71850_1_, int p_71850_2_, int p_71850_3_, int p_71850_4_, int p_71850_5_)
    {
        return p_71850_5_ == 2 && p_71850_1_.func_72809_s(p_71850_2_, p_71850_3_, p_71850_4_ + 1) ? true : (p_71850_5_ == 3 && p_71850_1_.func_72809_s(p_71850_2_, p_71850_3_, p_71850_4_ - 1) ? true : (p_71850_5_ == 4 && p_71850_1_.func_72809_s(p_71850_2_ + 1, p_71850_3_, p_71850_4_) ? true : p_71850_5_ == 5 && p_71850_1_.func_72809_s(p_71850_2_ - 1, p_71850_3_, p_71850_4_)));
    }

    public boolean func_71930_b(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_)
    {
        return p_71930_1_.func_72809_s(p_71930_2_ - 1, p_71930_3_, p_71930_4_) ? true : (p_71930_1_.func_72809_s(p_71930_2_ + 1, p_71930_3_, p_71930_4_) ? true : (p_71930_1_.func_72809_s(p_71930_2_, p_71930_3_, p_71930_4_ - 1) ? true : p_71930_1_.func_72809_s(p_71930_2_, p_71930_3_, p_71930_4_ + 1)));
    }

    public int func_85104_a(World p_85104_1_, int p_85104_2_, int p_85104_3_, int p_85104_4_, int p_85104_5_, float p_85104_6_, float p_85104_7_, float p_85104_8_, int p_85104_9_)
    {
        byte b0 = 0;

        if (p_85104_5_ == 2 && p_85104_1_.func_72887_b(p_85104_2_, p_85104_3_, p_85104_4_ + 1, true))
        {
            b0 = 2;
        }

        if (p_85104_5_ == 3 && p_85104_1_.func_72887_b(p_85104_2_, p_85104_3_, p_85104_4_ - 1, true))
        {
            b0 = 0;
        }

        if (p_85104_5_ == 4 && p_85104_1_.func_72887_b(p_85104_2_ + 1, p_85104_3_, p_85104_4_, true))
        {
            b0 = 1;
        }

        if (p_85104_5_ == 5 && p_85104_1_.func_72887_b(p_85104_2_ - 1, p_85104_3_, p_85104_4_, true))
        {
            b0 = 3;
        }

        return b0;
    }

    public void func_85105_g(World p_85105_1_, int p_85105_2_, int p_85105_3_, int p_85105_4_, int p_85105_5_)
    {
        this.func_72143_a(p_85105_1_, p_85105_2_, p_85105_3_, p_85105_4_, this.field_71990_ca, p_85105_5_, false, -1, 0);
    }

    public void func_71863_a(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
    {
        if (p_71863_5_ != this.field_71990_ca)
        {
            if (this.func_72144_l(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_))
            {
                int i1 = p_71863_1_.func_72805_g(p_71863_2_, p_71863_3_, p_71863_4_);
                int j1 = i1 & 3;
                boolean flag = false;

                if (!p_71863_1_.func_72809_s(p_71863_2_ - 1, p_71863_3_, p_71863_4_) && j1 == 3)
                {
                    flag = true;
                }

                if (!p_71863_1_.func_72809_s(p_71863_2_ + 1, p_71863_3_, p_71863_4_) && j1 == 1)
                {
                    flag = true;
                }

                if (!p_71863_1_.func_72809_s(p_71863_2_, p_71863_3_, p_71863_4_ - 1) && j1 == 0)
                {
                    flag = true;
                }

                if (!p_71863_1_.func_72809_s(p_71863_2_, p_71863_3_, p_71863_4_ + 1) && j1 == 2)
                {
                    flag = true;
                }

                if (flag)
                {
                    this.func_71897_c(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, i1, 0);
                    p_71863_1_.func_72859_e(p_71863_2_, p_71863_3_, p_71863_4_, 0);
                }
            }
        }
    }

    public void func_72143_a(World p_72143_1_, int p_72143_2_, int p_72143_3_, int p_72143_4_, int p_72143_5_, int p_72143_6_, boolean p_72143_7_, int p_72143_8_, int p_72143_9_)
    {
        int l1 = p_72143_6_ & 3;
        boolean flag1 = (p_72143_6_ & 4) == 4;
        boolean flag2 = (p_72143_6_ & 8) == 8;
        boolean flag3 = p_72143_5_ == Block.field_72064_bT.field_71990_ca;
        boolean flag4 = false;
        boolean flag5 = !p_72143_1_.func_72797_t(p_72143_2_, p_72143_3_ - 1, p_72143_4_);
        int i2 = Direction.field_71583_a[l1];
        int j2 = Direction.field_71581_b[l1];
        int k2 = 0;
        int[] aint = new int[42];
        int l2;
        int i3;
        int j3;
        int k3;
        int l3;

        for (i3 = 1; i3 < 42; ++i3)
        {
            l2 = p_72143_2_ + i2 * i3;
            k3 = p_72143_4_ + j2 * i3;
            j3 = p_72143_1_.func_72798_a(l2, p_72143_3_, k3);

            if (j3 == Block.field_72064_bT.field_71990_ca)
            {
                l3 = p_72143_1_.func_72805_g(l2, p_72143_3_, k3);

                if ((l3 & 3) == Direction.field_71580_e[l1])
                {
                    k2 = i3;
                }

                break;
            }

            if (j3 != Block.field_72062_bU.field_71990_ca && i3 != p_72143_8_)
            {
                aint[i3] = -1;
                flag3 = false;
            }
            else
            {
                l3 = i3 == p_72143_8_ ? p_72143_9_ : p_72143_1_.func_72805_g(l2, p_72143_3_, k3);
                boolean flag6 = (l3 & 8) != 8;
                boolean flag7 = (l3 & 1) == 1;
                boolean flag8 = (l3 & 2) == 2;
                flag3 &= flag8 == flag5;
                flag4 |= flag6 && flag7;
                aint[i3] = l3;

                if (i3 == p_72143_8_)
                {
                    p_72143_1_.func_72836_a(p_72143_2_, p_72143_3_, p_72143_4_, p_72143_5_, this.func_71859_p_());
                    flag3 &= flag6;
                }
            }
        }

        flag3 &= k2 > 1;
        flag4 &= flag3;
        i3 = (flag3 ? 4 : 0) | (flag4 ? 8 : 0);
        p_72143_6_ = l1 | i3;

        if (k2 > 0)
        {
            l2 = p_72143_2_ + i2 * k2;
            k3 = p_72143_4_ + j2 * k2;
            j3 = Direction.field_71580_e[l1];
            p_72143_1_.func_72921_c(l2, p_72143_3_, k3, j3 | i3);
            this.func_72146_e(p_72143_1_, l2, p_72143_3_, k3, j3);
            this.func_72145_a(p_72143_1_, l2, p_72143_3_, k3, flag3, flag4, flag1, flag2);
        }

        // CraftBukkit start
        org.bukkit.block.Block block = p_72143_1_.getWorld().getBlockAt(p_72143_2_, p_72143_3_, p_72143_4_);
        BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, 1, 0);
        p_72143_1_.getServer().getPluginManager().callEvent(eventRedstone);

        if (eventRedstone.getNewCurrent() > 0)
        {
            return;
        }

        // CraftBukkit end
        this.func_72145_a(p_72143_1_, p_72143_2_, p_72143_3_, p_72143_4_, flag3, flag4, flag1, flag2);

        if (p_72143_5_ > 0)
        {
            p_72143_1_.func_72921_c(p_72143_2_, p_72143_3_, p_72143_4_, p_72143_6_);

            if (p_72143_7_)
            {
                this.func_72146_e(p_72143_1_, p_72143_2_, p_72143_3_, p_72143_4_, l1);
            }
        }

        if (flag1 != flag3)
        {
            for (l2 = 1; l2 < k2; ++l2)
            {
                k3 = p_72143_2_ + i2 * l2;
                j3 = p_72143_4_ + j2 * l2;
                l3 = aint[l2];

                if (l3 >= 0)
                {
                    if (flag3)
                    {
                        l3 |= 4;
                    }
                    else
                    {
                        l3 &= -5;
                    }

                    p_72143_1_.func_72921_c(k3, p_72143_3_, j3, l3);
                }
            }
        }
    }

    public void func_71847_b(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
    {
        this.func_72143_a(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, this.field_71990_ca, p_71847_1_.func_72805_g(p_71847_2_, p_71847_3_, p_71847_4_), true, -1, 0);
    }

    private void func_72145_a(World p_72145_1_, int p_72145_2_, int p_72145_3_, int p_72145_4_, boolean p_72145_5_, boolean p_72145_6_, boolean p_72145_7_, boolean p_72145_8_)
    {
        if (p_72145_6_ && !p_72145_8_)
        {
            p_72145_1_.func_72908_a((double)p_72145_2_ + 0.5D, (double)p_72145_3_ + 0.1D, (double)p_72145_4_ + 0.5D, "random.click", 0.4F, 0.6F);
        }
        else if (!p_72145_6_ && p_72145_8_)
        {
            p_72145_1_.func_72908_a((double)p_72145_2_ + 0.5D, (double)p_72145_3_ + 0.1D, (double)p_72145_4_ + 0.5D, "random.click", 0.4F, 0.5F);
        }
        else if (p_72145_5_ && !p_72145_7_)
        {
            p_72145_1_.func_72908_a((double)p_72145_2_ + 0.5D, (double)p_72145_3_ + 0.1D, (double)p_72145_4_ + 0.5D, "random.click", 0.4F, 0.7F);
        }
        else if (!p_72145_5_ && p_72145_7_)
        {
            p_72145_1_.func_72908_a((double)p_72145_2_ + 0.5D, (double)p_72145_3_ + 0.1D, (double)p_72145_4_ + 0.5D, "random.bowhit", 0.4F, 1.2F / (p_72145_1_.field_73012_v.nextFloat() * 0.2F + 0.9F));
        }
    }

    private void func_72146_e(World p_72146_1_, int p_72146_2_, int p_72146_3_, int p_72146_4_, int p_72146_5_)
    {
        p_72146_1_.func_72898_h(p_72146_2_, p_72146_3_, p_72146_4_, this.field_71990_ca);

        if (p_72146_5_ == 3)
        {
            p_72146_1_.func_72898_h(p_72146_2_ - 1, p_72146_3_, p_72146_4_, this.field_71990_ca);
        }
        else if (p_72146_5_ == 1)
        {
            p_72146_1_.func_72898_h(p_72146_2_ + 1, p_72146_3_, p_72146_4_, this.field_71990_ca);
        }
        else if (p_72146_5_ == 0)
        {
            p_72146_1_.func_72898_h(p_72146_2_, p_72146_3_, p_72146_4_ - 1, this.field_71990_ca);
        }
        else if (p_72146_5_ == 2)
        {
            p_72146_1_.func_72898_h(p_72146_2_, p_72146_3_, p_72146_4_ + 1, this.field_71990_ca);
        }
    }

    private boolean func_72144_l(World p_72144_1_, int p_72144_2_, int p_72144_3_, int p_72144_4_)
    {
        if (!this.func_71930_b(p_72144_1_, p_72144_2_, p_72144_3_, p_72144_4_))
        {
            this.func_71897_c(p_72144_1_, p_72144_2_, p_72144_3_, p_72144_4_, p_72144_1_.func_72805_g(p_72144_2_, p_72144_3_, p_72144_4_), 0);
            p_72144_1_.func_72859_e(p_72144_2_, p_72144_3_, p_72144_4_, 0);
            return false;
        }
        else
        {
            return true;
        }
    }

    public void func_71902_a(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
    {
        int l = p_71902_1_.func_72805_g(p_71902_2_, p_71902_3_, p_71902_4_) & 3;
        float f = 0.1875F;

        if (l == 3)
        {
            this.func_71905_a(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
        }
        else if (l == 1)
        {
            this.func_71905_a(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
        }
        else if (l == 0)
        {
            this.func_71905_a(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
        }
        else if (l == 2)
        {
            this.func_71905_a(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
        }
    }

    public void func_71852_a(World p_71852_1_, int p_71852_2_, int p_71852_3_, int p_71852_4_, int p_71852_5_, int p_71852_6_)
    {
        boolean flag = (p_71852_6_ & 4) == 4;
        boolean flag1 = (p_71852_6_ & 8) == 8;

        if (flag || flag1)
        {
            this.func_72143_a(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_, 0, p_71852_6_, false, -1, 0);
        }

        if (flag1)
        {
            p_71852_1_.func_72898_h(p_71852_2_, p_71852_3_, p_71852_4_, this.field_71990_ca);
            int j1 = p_71852_6_ & 3;

            if (j1 == 3)
            {
                p_71852_1_.func_72898_h(p_71852_2_ - 1, p_71852_3_, p_71852_4_, this.field_71990_ca);
            }
            else if (j1 == 1)
            {
                p_71852_1_.func_72898_h(p_71852_2_ + 1, p_71852_3_, p_71852_4_, this.field_71990_ca);
            }
            else if (j1 == 0)
            {
                p_71852_1_.func_72898_h(p_71852_2_, p_71852_3_, p_71852_4_ - 1, this.field_71990_ca);
            }
            else if (j1 == 2)
            {
                p_71852_1_.func_72898_h(p_71852_2_, p_71852_3_, p_71852_4_ + 1, this.field_71990_ca);
            }
        }

        super.func_71852_a(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_, p_71852_5_, p_71852_6_);
    }

    public boolean func_71865_a(IBlockAccess p_71865_1_, int p_71865_2_, int p_71865_3_, int p_71865_4_, int p_71865_5_)
    {
        return (p_71865_1_.func_72805_g(p_71865_2_, p_71865_3_, p_71865_4_) & 8) == 8;
    }

    public boolean func_71855_c(IBlockAccess p_71855_1_, int p_71855_2_, int p_71855_3_, int p_71855_4_, int p_71855_5_)
    {
        int i1 = p_71855_1_.func_72805_g(p_71855_2_, p_71855_3_, p_71855_4_);

        if ((i1 & 8) != 8)
        {
            return false;
        }
        else
        {
            int j1 = i1 & 3;
            return j1 == 2 && p_71855_5_ == 2 ? true : (j1 == 0 && p_71855_5_ == 3 ? true : (j1 == 1 && p_71855_5_ == 4 ? true : j1 == 3 && p_71855_5_ == 5));
        }
    }

    public boolean func_71853_i()
    {
        return true;
    }
}
