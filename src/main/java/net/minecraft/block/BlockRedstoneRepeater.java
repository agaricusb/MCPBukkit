package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.bukkit.craftbukkit.event.CraftEventFactory; // CraftBukkit

public class BlockRedstoneRepeater extends BlockDirectional
{
    public static final double[] field_72223_a = new double[] { -0.0625D, 0.0625D, 0.1875D, 0.3125D};
    private static final int[] field_72221_b = new int[] {1, 2, 3, 4};
    private final boolean field_72222_c;

    protected BlockRedstoneRepeater(int p_i3934_1_, boolean p_i3934_2_)
    {
        super(p_i3934_1_, 6, Material.field_76265_p);
        this.field_72222_c = p_i3934_2_;
        this.func_71905_a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
    }

    public boolean func_71886_c()
    {
        return false;
    }

    public boolean func_71930_b(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_)
    {
        return !p_71930_1_.func_72797_t(p_71930_2_, p_71930_3_ - 1, p_71930_4_) ? false : super.func_71930_b(p_71930_1_, p_71930_2_, p_71930_3_, p_71930_4_);
    }

    public boolean func_71854_d(World p_71854_1_, int p_71854_2_, int p_71854_3_, int p_71854_4_)
    {
        return !p_71854_1_.func_72797_t(p_71854_2_, p_71854_3_ - 1, p_71854_4_) ? false : super.func_71854_d(p_71854_1_, p_71854_2_, p_71854_3_, p_71854_4_);
    }

    public void func_71847_b(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
    {
        int l = p_71847_1_.func_72805_g(p_71847_2_, p_71847_3_, p_71847_4_);
        boolean flag = this.func_82523_e(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, l);

        if (!flag)
        {
            boolean flag1 = this.func_72220_e(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, l);

            if (this.field_72222_c && !flag1)
            {
                // CraftBukkit start
                if (CraftEventFactory.callRedstoneChange(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, 15, 0).getNewCurrent() != 0)
                {
                    return;
                }

                // CraftBukkit end
                p_71847_1_.func_72832_d(p_71847_2_, p_71847_3_, p_71847_4_, Block.field_72010_bh.field_71990_ca, l);
            }
            else if (!this.field_72222_c)
            {
                // CraftBukkit start
                if (CraftEventFactory.callRedstoneChange(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, 0, 15).getNewCurrent() != 15)
                {
                    return;
                }

                // CraftBukkit end
                p_71847_1_.func_72832_d(p_71847_2_, p_71847_3_, p_71847_4_, Block.field_72011_bi.field_71990_ca, l);

                if (!flag1)
                {
                    int i1 = (l & 12) >> 2;
                    p_71847_1_.func_72836_a(p_71847_2_, p_71847_3_, p_71847_4_, Block.field_72011_bi.field_71990_ca, field_72221_b[i1] * 2);
                }
            }
        }
    }

    public int func_71858_a(int p_71858_1_, int p_71858_2_)
    {
        return p_71858_1_ == 0 ? (this.field_72222_c ? 99 : 115) : (p_71858_1_ == 1 ? (this.field_72222_c ? 147 : 131) : 5);
    }

    public int func_71857_b()
    {
        return 15;
    }

    public int func_71851_a(int p_71851_1_)
    {
        return this.func_71858_a(p_71851_1_, 0);
    }

    public boolean func_71855_c(IBlockAccess p_71855_1_, int p_71855_2_, int p_71855_3_, int p_71855_4_, int p_71855_5_)
    {
        return this.func_71865_a(p_71855_1_, p_71855_2_, p_71855_3_, p_71855_4_, p_71855_5_);
    }

    public boolean func_71865_a(IBlockAccess p_71865_1_, int p_71865_2_, int p_71865_3_, int p_71865_4_, int p_71865_5_)
    {
        if (!this.field_72222_c)
        {
            return false;
        }
        else
        {
            int i1 = func_72217_d(p_71865_1_.func_72805_g(p_71865_2_, p_71865_3_, p_71865_4_));
            return i1 == 0 && p_71865_5_ == 3 ? true : (i1 == 1 && p_71865_5_ == 4 ? true : (i1 == 2 && p_71865_5_ == 2 ? true : i1 == 3 && p_71865_5_ == 5));
        }
    }

    public void func_71863_a(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
    {
        if (!this.func_71854_d(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_))
        {
            this.func_71897_c(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, p_71863_1_.func_72805_g(p_71863_2_, p_71863_3_, p_71863_4_), 0);
            p_71863_1_.func_72859_e(p_71863_2_, p_71863_3_, p_71863_4_, 0);
            p_71863_1_.func_72898_h(p_71863_2_ + 1, p_71863_3_, p_71863_4_, this.field_71990_ca);
            p_71863_1_.func_72898_h(p_71863_2_ - 1, p_71863_3_, p_71863_4_, this.field_71990_ca);
            p_71863_1_.func_72898_h(p_71863_2_, p_71863_3_, p_71863_4_ + 1, this.field_71990_ca);
            p_71863_1_.func_72898_h(p_71863_2_, p_71863_3_, p_71863_4_ - 1, this.field_71990_ca);
            p_71863_1_.func_72898_h(p_71863_2_, p_71863_3_ - 1, p_71863_4_, this.field_71990_ca);
            p_71863_1_.func_72898_h(p_71863_2_, p_71863_3_ + 1, p_71863_4_, this.field_71990_ca);
        }
        else
        {
            int i1 = p_71863_1_.func_72805_g(p_71863_2_, p_71863_3_, p_71863_4_);
            boolean flag = this.func_82523_e(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, i1);

            if (!flag)
            {
                boolean flag1 = this.func_72220_e(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, i1);
                int j1 = (i1 & 12) >> 2;

                if (this.field_72222_c && !flag1 || !this.field_72222_c && flag1)
                {
                    byte b0 = 0;

                    if (this.func_83011_d(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, i1))
                    {
                        b0 = -1;
                    }

                    p_71863_1_.func_82740_a(p_71863_2_, p_71863_3_, p_71863_4_, this.field_71990_ca, field_72221_b[j1] * 2, b0);
                }
            }
        }
    }

    private boolean func_72220_e(World p_72220_1_, int p_72220_2_, int p_72220_3_, int p_72220_4_, int p_72220_5_)
    {
        int i1 = func_72217_d(p_72220_5_);

        switch (i1)
        {
            case 0:
                return p_72220_1_.func_72878_l(p_72220_2_, p_72220_3_, p_72220_4_ + 1, 3) || p_72220_1_.func_72798_a(p_72220_2_, p_72220_3_, p_72220_4_ + 1) == Block.field_72075_av.field_71990_ca && p_72220_1_.func_72805_g(p_72220_2_, p_72220_3_, p_72220_4_ + 1) > 0;
            case 1:
                return p_72220_1_.func_72878_l(p_72220_2_ - 1, p_72220_3_, p_72220_4_, 4) || p_72220_1_.func_72798_a(p_72220_2_ - 1, p_72220_3_, p_72220_4_) == Block.field_72075_av.field_71990_ca && p_72220_1_.func_72805_g(p_72220_2_ - 1, p_72220_3_, p_72220_4_) > 0;
            case 2:
                return p_72220_1_.func_72878_l(p_72220_2_, p_72220_3_, p_72220_4_ - 1, 2) || p_72220_1_.func_72798_a(p_72220_2_, p_72220_3_, p_72220_4_ - 1) == Block.field_72075_av.field_71990_ca && p_72220_1_.func_72805_g(p_72220_2_, p_72220_3_, p_72220_4_ - 1) > 0;
            case 3:
                return p_72220_1_.func_72878_l(p_72220_2_ + 1, p_72220_3_, p_72220_4_, 5) || p_72220_1_.func_72798_a(p_72220_2_ + 1, p_72220_3_, p_72220_4_) == Block.field_72075_av.field_71990_ca && p_72220_1_.func_72805_g(p_72220_2_ + 1, p_72220_3_, p_72220_4_) > 0;
            default:
                return false;
        }
    }

    public boolean func_82523_e(IBlockAccess p_82523_1_, int p_82523_2_, int p_82523_3_, int p_82523_4_, int p_82523_5_)
    {
        int i1 = func_72217_d(p_82523_5_);

        switch (i1)
        {
            case 0:
            case 2:
                return p_82523_1_.func_72879_k(p_82523_2_ - 1, p_82523_3_, p_82523_4_, 4) && func_82524_c(p_82523_1_.func_72798_a(p_82523_2_ - 1, p_82523_3_, p_82523_4_)) || p_82523_1_.func_72879_k(p_82523_2_ + 1, p_82523_3_, p_82523_4_, 5) && func_82524_c(p_82523_1_.func_72798_a(p_82523_2_ + 1, p_82523_3_, p_82523_4_));
            case 1:
            case 3:
                return p_82523_1_.func_72879_k(p_82523_2_, p_82523_3_, p_82523_4_ + 1, 3) && func_82524_c(p_82523_1_.func_72798_a(p_82523_2_, p_82523_3_, p_82523_4_ + 1)) || p_82523_1_.func_72879_k(p_82523_2_, p_82523_3_, p_82523_4_ - 1, 2) && func_82524_c(p_82523_1_.func_72798_a(p_82523_2_, p_82523_3_, p_82523_4_ - 1));
            default:
                return false;
        }
    }

    public boolean func_71903_a(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
    {
        int i1 = p_71903_1_.func_72805_g(p_71903_2_, p_71903_3_, p_71903_4_);
        int j1 = (i1 & 12) >> 2;
        j1 = j1 + 1 << 2 & 12;
        p_71903_1_.func_72921_c(p_71903_2_, p_71903_3_, p_71903_4_, j1 | i1 & 3);
        return true;
    }

    public boolean func_71853_i()
    {
        return true;
    }

    public void func_71860_a(World p_71860_1_, int p_71860_2_, int p_71860_3_, int p_71860_4_, EntityLiving p_71860_5_)
    {
        int l = ((MathHelper.func_76128_c((double)(p_71860_5_.field_70177_z * 4.0F / 360.0F) + 0.5D) & 3) + 2) % 4;
        p_71860_1_.func_72921_c(p_71860_2_, p_71860_3_, p_71860_4_, l);
        boolean flag = this.func_72220_e(p_71860_1_, p_71860_2_, p_71860_3_, p_71860_4_, l);

        if (flag)
        {
            p_71860_1_.func_72836_a(p_71860_2_, p_71860_3_, p_71860_4_, this.field_71990_ca, 1);
        }
    }

    public void func_71861_g(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
    {
        p_71861_1_.func_72898_h(p_71861_2_ + 1, p_71861_3_, p_71861_4_, this.field_71990_ca);
        p_71861_1_.func_72898_h(p_71861_2_ - 1, p_71861_3_, p_71861_4_, this.field_71990_ca);
        p_71861_1_.func_72898_h(p_71861_2_, p_71861_3_, p_71861_4_ + 1, this.field_71990_ca);
        p_71861_1_.func_72898_h(p_71861_2_, p_71861_3_, p_71861_4_ - 1, this.field_71990_ca);
        p_71861_1_.func_72898_h(p_71861_2_, p_71861_3_ - 1, p_71861_4_, this.field_71990_ca);
        p_71861_1_.func_72898_h(p_71861_2_, p_71861_3_ + 1, p_71861_4_, this.field_71990_ca);
    }

    public void func_71898_d(World p_71898_1_, int p_71898_2_, int p_71898_3_, int p_71898_4_, int p_71898_5_)
    {
        if (this.field_72222_c)
        {
            p_71898_1_.func_72898_h(p_71898_2_ + 1, p_71898_3_, p_71898_4_, this.field_71990_ca);
            p_71898_1_.func_72898_h(p_71898_2_ - 1, p_71898_3_, p_71898_4_, this.field_71990_ca);
            p_71898_1_.func_72898_h(p_71898_2_, p_71898_3_, p_71898_4_ + 1, this.field_71990_ca);
            p_71898_1_.func_72898_h(p_71898_2_, p_71898_3_, p_71898_4_ - 1, this.field_71990_ca);
            p_71898_1_.func_72898_h(p_71898_2_, p_71898_3_ - 1, p_71898_4_, this.field_71990_ca);
            p_71898_1_.func_72898_h(p_71898_2_, p_71898_3_ + 1, p_71898_4_, this.field_71990_ca);
        }

        super.func_71898_d(p_71898_1_, p_71898_2_, p_71898_3_, p_71898_4_, p_71898_5_);
    }

    public boolean func_71926_d()
    {
        return false;
    }

    public int func_71885_a(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
    {
        return Item.field_77742_bb.field_77779_bT;
    }

    public static boolean func_82524_c(int p_82524_0_)
    {
        return p_82524_0_ == Block.field_72011_bi.field_71990_ca || p_82524_0_ == Block.field_72010_bh.field_71990_ca;
    }

    public boolean func_83011_d(World p_83011_1_, int p_83011_2_, int p_83011_3_, int p_83011_4_, int p_83011_5_)
    {
        int i1 = func_72217_d(p_83011_5_);

        if (func_82524_c(p_83011_1_.func_72798_a(p_83011_2_ - Direction.field_71583_a[i1], p_83011_3_, p_83011_4_ - Direction.field_71581_b[i1])))
        {
            int j1 = p_83011_1_.func_72805_g(p_83011_2_ - Direction.field_71583_a[i1], p_83011_3_, p_83011_4_ - Direction.field_71581_b[i1]);
            int k1 = func_72217_d(j1);
            return k1 != i1;
        }
        else
        {
            return false;
        }
    }
}
