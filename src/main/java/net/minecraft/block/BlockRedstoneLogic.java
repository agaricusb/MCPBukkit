package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.bukkit.craftbukkit.event.CraftEventFactory; // CraftBukkit

public abstract class BlockRedstoneLogic extends BlockDirectional
{
    protected final boolean field_72222_c;

    protected BlockRedstoneLogic(int p_i9012_1_, boolean p_i9012_2_)
    {
        super(p_i9012_1_, Material.field_76265_p);
        this.field_72222_c = p_i9012_2_;
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

        if (!this.func_94476_e((IBlockAccess) p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, l))
        {
            boolean flag = this.func_94478_d(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, l);

            if (this.field_72222_c && !flag)
            {
                // CraftBukkit start
                if (CraftEventFactory.callRedstoneChange(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, 15, 0).getNewCurrent() != 0)
                {
                    return;
                }

                // CraftBukkit end
                p_71847_1_.func_72832_d(p_71847_2_, p_71847_3_, p_71847_4_, this.func_94484_i().field_71990_ca, l, 2);
            }
            else if (!this.field_72222_c)
            {
                // CraftBukkit start
                if (CraftEventFactory.callRedstoneChange(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, 0, 15).getNewCurrent() != 15)
                {
                    return;
                }

                // CraftBukkit end
                p_71847_1_.func_72832_d(p_71847_2_, p_71847_3_, p_71847_4_, this.func_94485_e().field_71990_ca, l, 2);

                if (!flag)
                {
                    p_71847_1_.func_82740_a(p_71847_2_, p_71847_3_, p_71847_4_, this.func_94485_e().field_71990_ca, this.func_94486_g(l), -1);
                }
            }
        }
    }

    public int func_71857_b()
    {
        return 36;
    }

    protected boolean func_96470_c(int p_96470_1_)
    {
        return this.field_72222_c;
    }

    public int func_71855_c(IBlockAccess p_71855_1_, int p_71855_2_, int p_71855_3_, int p_71855_4_, int p_71855_5_)
    {
        return this.func_71865_a(p_71855_1_, p_71855_2_, p_71855_3_, p_71855_4_, p_71855_5_);
    }

    public int func_71865_a(IBlockAccess p_71865_1_, int p_71865_2_, int p_71865_3_, int p_71865_4_, int p_71865_5_)
    {
        int i1 = p_71865_1_.func_72805_g(p_71865_2_, p_71865_3_, p_71865_4_);

        if (!this.func_96470_c(i1))
        {
            return 0;
        }
        else
        {
            int j1 = func_72217_d(i1);
            return j1 == 0 && p_71865_5_ == 3 ? this.func_94480_d(p_71865_1_, p_71865_2_, p_71865_3_, p_71865_4_, i1) : (j1 == 1 && p_71865_5_ == 4 ? this.func_94480_d(p_71865_1_, p_71865_2_, p_71865_3_, p_71865_4_, i1) : (j1 == 2 && p_71865_5_ == 2 ? this.func_94480_d(p_71865_1_, p_71865_2_, p_71865_3_, p_71865_4_, i1) : (j1 == 3 && p_71865_5_ == 5 ? this.func_94480_d(p_71865_1_, p_71865_2_, p_71865_3_, p_71865_4_, i1) : 0)));
        }
    }

    public void func_71863_a(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
    {
        if (!this.func_71854_d(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_))
        {
            this.func_71897_c(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, p_71863_1_.func_72805_g(p_71863_2_, p_71863_3_, p_71863_4_), 0);
            p_71863_1_.func_94571_i(p_71863_2_, p_71863_3_, p_71863_4_);
            p_71863_1_.func_72898_h(p_71863_2_ + 1, p_71863_3_, p_71863_4_, this.field_71990_ca);
            p_71863_1_.func_72898_h(p_71863_2_ - 1, p_71863_3_, p_71863_4_, this.field_71990_ca);
            p_71863_1_.func_72898_h(p_71863_2_, p_71863_3_, p_71863_4_ + 1, this.field_71990_ca);
            p_71863_1_.func_72898_h(p_71863_2_, p_71863_3_, p_71863_4_ - 1, this.field_71990_ca);
            p_71863_1_.func_72898_h(p_71863_2_, p_71863_3_ - 1, p_71863_4_, this.field_71990_ca);
            p_71863_1_.func_72898_h(p_71863_2_, p_71863_3_ + 1, p_71863_4_, this.field_71990_ca);
        }
        else
        {
            this.func_94479_f(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, p_71863_5_);
        }
    }

    protected void func_94479_f(World p_94479_1_, int p_94479_2_, int p_94479_3_, int p_94479_4_, int p_94479_5_)
    {
        int i1 = p_94479_1_.func_72805_g(p_94479_2_, p_94479_3_, p_94479_4_);

        if (!this.func_94476_e((IBlockAccess) p_94479_1_, p_94479_2_, p_94479_3_, p_94479_4_, i1))
        {
            boolean flag = this.func_94478_d(p_94479_1_, p_94479_2_, p_94479_3_, p_94479_4_, i1);

            if ((this.field_72222_c && !flag || !this.field_72222_c && flag) && !p_94479_1_.func_94573_a(p_94479_2_, p_94479_3_, p_94479_4_, this.field_71990_ca))
            {
                byte b0 = -1;

                if (this.func_83011_d(p_94479_1_, p_94479_2_, p_94479_3_, p_94479_4_, i1))
                {
                    b0 = -3;
                }
                else if (this.field_72222_c)
                {
                    b0 = -2;
                }

                p_94479_1_.func_82740_a(p_94479_2_, p_94479_3_, p_94479_4_, this.field_71990_ca, this.func_94481_j_(i1), b0);
            }
        }
    }

    public boolean func_94476_e(IBlockAccess p_94476_1_, int p_94476_2_, int p_94476_3_, int p_94476_4_, int p_94476_5_)
    {
        return false;
    }

    protected boolean func_94478_d(World p_94478_1_, int p_94478_2_, int p_94478_3_, int p_94478_4_, int p_94478_5_)
    {
        return this.func_72220_e(p_94478_1_, p_94478_2_, p_94478_3_, p_94478_4_, p_94478_5_) > 0;
    }

    protected int func_72220_e(World p_72220_1_, int p_72220_2_, int p_72220_3_, int p_72220_4_, int p_72220_5_)
    {
        int i1 = func_72217_d(p_72220_5_);
        int j1 = p_72220_2_ + Direction.field_71583_a[i1];
        int k1 = p_72220_4_ + Direction.field_71581_b[i1];
        int l1 = p_72220_1_.func_72878_l(j1, p_72220_3_, k1, Direction.field_71582_c[i1]);
        return l1 >= 15 ? l1 : Math.max(l1, p_72220_1_.func_72798_a(j1, p_72220_3_, k1) == Block.field_72075_av.field_71990_ca ? p_72220_1_.func_72805_g(j1, p_72220_3_, k1) : 0);
    }

    protected int func_94482_f(IBlockAccess p_94482_1_, int p_94482_2_, int p_94482_3_, int p_94482_4_, int p_94482_5_)
    {
        int i1 = func_72217_d(p_94482_5_);

        switch (i1)
        {
            case 0:
            case 2:
                return Math.max(this.func_94488_g(p_94482_1_, p_94482_2_ - 1, p_94482_3_, p_94482_4_, 4), this.func_94488_g(p_94482_1_, p_94482_2_ + 1, p_94482_3_, p_94482_4_, 5));

            case 1:
            case 3:
                return Math.max(this.func_94488_g(p_94482_1_, p_94482_2_, p_94482_3_, p_94482_4_ + 1, 3), this.func_94488_g(p_94482_1_, p_94482_2_, p_94482_3_, p_94482_4_ - 1, 2));

            default:
                return 0;
        }
    }

    protected int func_94488_g(IBlockAccess p_94488_1_, int p_94488_2_, int p_94488_3_, int p_94488_4_, int p_94488_5_)
    {
        int i1 = p_94488_1_.func_72798_a(p_94488_2_, p_94488_3_, p_94488_4_);
        return this.func_94477_d(i1) ? (i1 == Block.field_72075_av.field_71990_ca ? p_94488_1_.func_72805_g(p_94488_2_, p_94488_3_, p_94488_4_) : p_94488_1_.func_72879_k(p_94488_2_, p_94488_3_, p_94488_4_, p_94488_5_)) : 0;
    }

    public boolean func_71853_i()
    {
        return true;
    }

    public void func_71860_a(World p_71860_1_, int p_71860_2_, int p_71860_3_, int p_71860_4_, EntityLiving p_71860_5_, ItemStack p_71860_6_)
    {
        int l = ((MathHelper.func_76128_c((double)(p_71860_5_.field_70177_z * 4.0F / 360.0F) + 0.5D) & 3) + 2) % 4;
        p_71860_1_.func_72921_c(p_71860_2_, p_71860_3_, p_71860_4_, l, 3);
        boolean flag = this.func_94478_d(p_71860_1_, p_71860_2_, p_71860_3_, p_71860_4_, l);

        if (flag)
        {
            p_71860_1_.func_72836_a(p_71860_2_, p_71860_3_, p_71860_4_, this.field_71990_ca, 1);
        }
    }

    public void func_71861_g(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
    {
        this.func_94483_i_(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_);
    }

    protected void func_94483_i_(World p_94483_1_, int p_94483_2_, int p_94483_3_, int p_94483_4_)
    {
        int l = func_72217_d(p_94483_1_.func_72805_g(p_94483_2_, p_94483_3_, p_94483_4_));

        if (l == 1)
        {
            p_94483_1_.func_72821_m(p_94483_2_ + 1, p_94483_3_, p_94483_4_, this.field_71990_ca);
            p_94483_1_.func_96439_d(p_94483_2_ + 1, p_94483_3_, p_94483_4_, this.field_71990_ca, 4);
        }

        if (l == 3)
        {
            p_94483_1_.func_72821_m(p_94483_2_ - 1, p_94483_3_, p_94483_4_, this.field_71990_ca);
            p_94483_1_.func_96439_d(p_94483_2_ - 1, p_94483_3_, p_94483_4_, this.field_71990_ca, 5);
        }

        if (l == 2)
        {
            p_94483_1_.func_72821_m(p_94483_2_, p_94483_3_, p_94483_4_ + 1, this.field_71990_ca);
            p_94483_1_.func_96439_d(p_94483_2_, p_94483_3_, p_94483_4_ + 1, this.field_71990_ca, 2);
        }

        if (l == 0)
        {
            p_94483_1_.func_72821_m(p_94483_2_, p_94483_3_, p_94483_4_ - 1, this.field_71990_ca);
            p_94483_1_.func_96439_d(p_94483_2_, p_94483_3_, p_94483_4_ - 1, this.field_71990_ca, 3);
        }
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

    protected boolean func_94477_d(int p_94477_1_)
    {
        Block block = Block.field_71973_m[p_94477_1_];
        return block != null && block.func_71853_i();
    }

    protected int func_94480_d(IBlockAccess p_94480_1_, int p_94480_2_, int p_94480_3_, int p_94480_4_, int p_94480_5_)
    {
        return 15;
    }

    public static boolean func_82524_c(int p_82524_0_)
    {
        return Block.field_72010_bh.func_94487_f(p_82524_0_) || Block.field_94346_cn.func_94487_f(p_82524_0_);
    }

    public boolean func_94487_f(int p_94487_1_)
    {
        return p_94487_1_ == this.func_94485_e().field_71990_ca || p_94487_1_ == this.func_94484_i().field_71990_ca;
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

    protected int func_94486_g(int p_94486_1_)
    {
        return this.func_94481_j_(p_94486_1_);
    }

    protected abstract int func_94481_j_(int i);

    protected abstract BlockRedstoneLogic func_94485_e();

    protected abstract BlockRedstoneLogic func_94484_i();

    public boolean func_94334_h(int p_94334_1_)
    {
        return this.func_94487_f(p_94334_1_);
    }
}
