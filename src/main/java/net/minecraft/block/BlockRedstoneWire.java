package net.minecraft.block;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Direction;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.bukkit.event.block.BlockRedstoneEvent; // CraftBukkit

public class BlockRedstoneWire extends Block
{
    private boolean field_72175_a = true;
    private Set field_72174_b = new HashSet();

    public BlockRedstoneWire(int p_i9085_1_)
    {
        super(p_i9085_1_, Material.field_76265_p);
        this.func_71905_a(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
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
        return 5;
    }

    public boolean func_71930_b(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_)
    {
        return p_71930_1_.func_72797_t(p_71930_2_, p_71930_3_ - 1, p_71930_4_) || p_71930_1_.func_72798_a(p_71930_2_, p_71930_3_ - 1, p_71930_4_) == Block.field_72014_bd.field_71990_ca;
    }

    private void func_72168_l(World p_72168_1_, int p_72168_2_, int p_72168_3_, int p_72168_4_)
    {
        this.func_72171_a(p_72168_1_, p_72168_2_, p_72168_3_, p_72168_4_, p_72168_2_, p_72168_3_, p_72168_4_);
        ArrayList arraylist = new ArrayList(this.field_72174_b);
        this.field_72174_b.clear();

        for (int l = 0; l < arraylist.size(); ++l)
        {
            ChunkPosition chunkposition = (ChunkPosition)arraylist.get(l);
            p_72168_1_.func_72898_h(chunkposition.field_76930_a, chunkposition.field_76928_b, chunkposition.field_76929_c, this.field_71990_ca);
        }
    }

    private void func_72171_a(World p_72171_1_, int p_72171_2_, int p_72171_3_, int p_72171_4_, int p_72171_5_, int p_72171_6_, int p_72171_7_)
    {
        int k1 = p_72171_1_.func_72805_g(p_72171_2_, p_72171_3_, p_72171_4_);
        byte b0 = 0;
        int l1 = this.func_72170_e(p_72171_1_, p_72171_5_, p_72171_6_, p_72171_7_, b0);
        this.field_72175_a = false;
        int i2 = p_72171_1_.func_94572_D(p_72171_2_, p_72171_3_, p_72171_4_);
        this.field_72175_a = true;

        if (i2 > 0 && i2 > l1 - 1)
        {
            l1 = i2;
        }

        int j2 = 0;

        for (int k2 = 0; k2 < 4; ++k2)
        {
            int l2 = p_72171_2_;
            int i3 = p_72171_4_;

            if (k2 == 0)
            {
                l2 = p_72171_2_ - 1;
            }

            if (k2 == 1)
            {
                ++l2;
            }

            if (k2 == 2)
            {
                i3 = p_72171_4_ - 1;
            }

            if (k2 == 3)
            {
                ++i3;
            }

            if (l2 != p_72171_5_ || i3 != p_72171_7_)
            {
                j2 = this.func_72170_e(p_72171_1_, l2, p_72171_3_, i3, j2);
            }

            if (p_72171_1_.func_72809_s(l2, p_72171_3_, i3) && !p_72171_1_.func_72809_s(p_72171_2_, p_72171_3_ + 1, p_72171_4_))
            {
                if ((l2 != p_72171_5_ || i3 != p_72171_7_) && p_72171_3_ >= p_72171_6_)
                {
                    j2 = this.func_72170_e(p_72171_1_, l2, p_72171_3_ + 1, i3, j2);
                }
            }
            else if (!p_72171_1_.func_72809_s(l2, p_72171_3_, i3) && (l2 != p_72171_5_ || i3 != p_72171_7_) && p_72171_3_ <= p_72171_6_)
            {
                j2 = this.func_72170_e(p_72171_1_, l2, p_72171_3_ - 1, i3, j2);
            }
        }

        if (j2 > l1)
        {
            l1 = j2 - 1;
        }
        else if (l1 > 0)
        {
            --l1;
        }
        else
        {
            l1 = 0;
        }

        if (i2 > l1 - 1)
        {
            l1 = i2;
        }

        // CraftBukkit start
        if (k1 != l1)
        {
            BlockRedstoneEvent event = new BlockRedstoneEvent(p_72171_1_.getWorld().getBlockAt(p_72171_2_, p_72171_3_, p_72171_4_), k1, l1);
            p_72171_1_.getServer().getPluginManager().callEvent(event);
            l1 = event.getNewCurrent();
        }

        // CraftBukkit end

        if (k1 != l1)
        {
            p_72171_1_.func_72921_c(p_72171_2_, p_72171_3_, p_72171_4_, l1, 2);
            this.field_72174_b.add(new ChunkPosition(p_72171_2_, p_72171_3_, p_72171_4_));
            this.field_72174_b.add(new ChunkPosition(p_72171_2_ - 1, p_72171_3_, p_72171_4_));
            this.field_72174_b.add(new ChunkPosition(p_72171_2_ + 1, p_72171_3_, p_72171_4_));
            this.field_72174_b.add(new ChunkPosition(p_72171_2_, p_72171_3_ - 1, p_72171_4_));
            this.field_72174_b.add(new ChunkPosition(p_72171_2_, p_72171_3_ + 1, p_72171_4_));
            this.field_72174_b.add(new ChunkPosition(p_72171_2_, p_72171_3_, p_72171_4_ - 1));
            this.field_72174_b.add(new ChunkPosition(p_72171_2_, p_72171_3_, p_72171_4_ + 1));
        }
    }

    private void func_72172_n(World p_72172_1_, int p_72172_2_, int p_72172_3_, int p_72172_4_)
    {
        if (p_72172_1_.func_72798_a(p_72172_2_, p_72172_3_, p_72172_4_) == this.field_71990_ca)
        {
            p_72172_1_.func_72898_h(p_72172_2_, p_72172_3_, p_72172_4_, this.field_71990_ca);
            p_72172_1_.func_72898_h(p_72172_2_ - 1, p_72172_3_, p_72172_4_, this.field_71990_ca);
            p_72172_1_.func_72898_h(p_72172_2_ + 1, p_72172_3_, p_72172_4_, this.field_71990_ca);
            p_72172_1_.func_72898_h(p_72172_2_, p_72172_3_, p_72172_4_ - 1, this.field_71990_ca);
            p_72172_1_.func_72898_h(p_72172_2_, p_72172_3_, p_72172_4_ + 1, this.field_71990_ca);
            p_72172_1_.func_72898_h(p_72172_2_, p_72172_3_ - 1, p_72172_4_, this.field_71990_ca);
            p_72172_1_.func_72898_h(p_72172_2_, p_72172_3_ + 1, p_72172_4_, this.field_71990_ca);
        }
    }

    public void func_71861_g(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
    {
        super.func_71861_g(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_);

        if (!p_71861_1_.field_72995_K)
        {
            this.func_72168_l(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_);
            p_71861_1_.func_72898_h(p_71861_2_, p_71861_3_ + 1, p_71861_4_, this.field_71990_ca);
            p_71861_1_.func_72898_h(p_71861_2_, p_71861_3_ - 1, p_71861_4_, this.field_71990_ca);
            this.func_72172_n(p_71861_1_, p_71861_2_ - 1, p_71861_3_, p_71861_4_);
            this.func_72172_n(p_71861_1_, p_71861_2_ + 1, p_71861_3_, p_71861_4_);
            this.func_72172_n(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_ - 1);
            this.func_72172_n(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_ + 1);

            if (p_71861_1_.func_72809_s(p_71861_2_ - 1, p_71861_3_, p_71861_4_))
            {
                this.func_72172_n(p_71861_1_, p_71861_2_ - 1, p_71861_3_ + 1, p_71861_4_);
            }
            else
            {
                this.func_72172_n(p_71861_1_, p_71861_2_ - 1, p_71861_3_ - 1, p_71861_4_);
            }

            if (p_71861_1_.func_72809_s(p_71861_2_ + 1, p_71861_3_, p_71861_4_))
            {
                this.func_72172_n(p_71861_1_, p_71861_2_ + 1, p_71861_3_ + 1, p_71861_4_);
            }
            else
            {
                this.func_72172_n(p_71861_1_, p_71861_2_ + 1, p_71861_3_ - 1, p_71861_4_);
            }

            if (p_71861_1_.func_72809_s(p_71861_2_, p_71861_3_, p_71861_4_ - 1))
            {
                this.func_72172_n(p_71861_1_, p_71861_2_, p_71861_3_ + 1, p_71861_4_ - 1);
            }
            else
            {
                this.func_72172_n(p_71861_1_, p_71861_2_, p_71861_3_ - 1, p_71861_4_ - 1);
            }

            if (p_71861_1_.func_72809_s(p_71861_2_, p_71861_3_, p_71861_4_ + 1))
            {
                this.func_72172_n(p_71861_1_, p_71861_2_, p_71861_3_ + 1, p_71861_4_ + 1);
            }
            else
            {
                this.func_72172_n(p_71861_1_, p_71861_2_, p_71861_3_ - 1, p_71861_4_ + 1);
            }
        }
    }

    public void func_71852_a(World p_71852_1_, int p_71852_2_, int p_71852_3_, int p_71852_4_, int p_71852_5_, int p_71852_6_)
    {
        super.func_71852_a(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_, p_71852_5_, p_71852_6_);

        if (!p_71852_1_.field_72995_K)
        {
            p_71852_1_.func_72898_h(p_71852_2_, p_71852_3_ + 1, p_71852_4_, this.field_71990_ca);
            p_71852_1_.func_72898_h(p_71852_2_, p_71852_3_ - 1, p_71852_4_, this.field_71990_ca);
            p_71852_1_.func_72898_h(p_71852_2_ + 1, p_71852_3_, p_71852_4_, this.field_71990_ca);
            p_71852_1_.func_72898_h(p_71852_2_ - 1, p_71852_3_, p_71852_4_, this.field_71990_ca);
            p_71852_1_.func_72898_h(p_71852_2_, p_71852_3_, p_71852_4_ + 1, this.field_71990_ca);
            p_71852_1_.func_72898_h(p_71852_2_, p_71852_3_, p_71852_4_ - 1, this.field_71990_ca);
            this.func_72168_l(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_);
            this.func_72172_n(p_71852_1_, p_71852_2_ - 1, p_71852_3_, p_71852_4_);
            this.func_72172_n(p_71852_1_, p_71852_2_ + 1, p_71852_3_, p_71852_4_);
            this.func_72172_n(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_ - 1);
            this.func_72172_n(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_ + 1);

            if (p_71852_1_.func_72809_s(p_71852_2_ - 1, p_71852_3_, p_71852_4_))
            {
                this.func_72172_n(p_71852_1_, p_71852_2_ - 1, p_71852_3_ + 1, p_71852_4_);
            }
            else
            {
                this.func_72172_n(p_71852_1_, p_71852_2_ - 1, p_71852_3_ - 1, p_71852_4_);
            }

            if (p_71852_1_.func_72809_s(p_71852_2_ + 1, p_71852_3_, p_71852_4_))
            {
                this.func_72172_n(p_71852_1_, p_71852_2_ + 1, p_71852_3_ + 1, p_71852_4_);
            }
            else
            {
                this.func_72172_n(p_71852_1_, p_71852_2_ + 1, p_71852_3_ - 1, p_71852_4_);
            }

            if (p_71852_1_.func_72809_s(p_71852_2_, p_71852_3_, p_71852_4_ - 1))
            {
                this.func_72172_n(p_71852_1_, p_71852_2_, p_71852_3_ + 1, p_71852_4_ - 1);
            }
            else
            {
                this.func_72172_n(p_71852_1_, p_71852_2_, p_71852_3_ - 1, p_71852_4_ - 1);
            }

            if (p_71852_1_.func_72809_s(p_71852_2_, p_71852_3_, p_71852_4_ + 1))
            {
                this.func_72172_n(p_71852_1_, p_71852_2_, p_71852_3_ + 1, p_71852_4_ + 1);
            }
            else
            {
                this.func_72172_n(p_71852_1_, p_71852_2_, p_71852_3_ - 1, p_71852_4_ + 1);
            }
        }
    }

    // CraftBukkit - private -> public
    public int func_72170_e(World p_72170_1_, int p_72170_2_, int p_72170_3_, int p_72170_4_, int p_72170_5_)
    {
        if (p_72170_1_.func_72798_a(p_72170_2_, p_72170_3_, p_72170_4_) != this.field_71990_ca)
        {
            return p_72170_5_;
        }
        else
        {
            int i1 = p_72170_1_.func_72805_g(p_72170_2_, p_72170_3_, p_72170_4_);
            return i1 > p_72170_5_ ? i1 : p_72170_5_;
        }
    }

    public void func_71863_a(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
    {
        if (!p_71863_1_.field_72995_K)
        {
            boolean flag = this.func_71930_b(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_);

            if (flag)
            {
                this.func_72168_l(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_);
            }
            else
            {
                this.func_71897_c(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, 0, 0);
                p_71863_1_.func_94571_i(p_71863_2_, p_71863_3_, p_71863_4_);
            }

            super.func_71863_a(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, p_71863_5_);
        }
    }

    public int func_71885_a(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
    {
        return Item.field_77767_aC.field_77779_bT;
    }

    public int func_71855_c(IBlockAccess p_71855_1_, int p_71855_2_, int p_71855_3_, int p_71855_4_, int p_71855_5_)
    {
        return !this.field_72175_a ? 0 : this.func_71865_a(p_71855_1_, p_71855_2_, p_71855_3_, p_71855_4_, p_71855_5_);
    }

    public int func_71865_a(IBlockAccess p_71865_1_, int p_71865_2_, int p_71865_3_, int p_71865_4_, int p_71865_5_)
    {
        if (!this.field_72175_a)
        {
            return 0;
        }
        else
        {
            int i1 = p_71865_1_.func_72805_g(p_71865_2_, p_71865_3_, p_71865_4_);

            if (i1 == 0)
            {
                return 0;
            }
            else if (p_71865_5_ == 1)
            {
                return i1;
            }
            else
            {
                boolean flag = func_72169_f(p_71865_1_, p_71865_2_ - 1, p_71865_3_, p_71865_4_, 1) || !p_71865_1_.func_72809_s(p_71865_2_ - 1, p_71865_3_, p_71865_4_) && func_72169_f(p_71865_1_, p_71865_2_ - 1, p_71865_3_ - 1, p_71865_4_, -1);
                boolean flag1 = func_72169_f(p_71865_1_, p_71865_2_ + 1, p_71865_3_, p_71865_4_, 3) || !p_71865_1_.func_72809_s(p_71865_2_ + 1, p_71865_3_, p_71865_4_) && func_72169_f(p_71865_1_, p_71865_2_ + 1, p_71865_3_ - 1, p_71865_4_, -1);
                boolean flag2 = func_72169_f(p_71865_1_, p_71865_2_, p_71865_3_, p_71865_4_ - 1, 2) || !p_71865_1_.func_72809_s(p_71865_2_, p_71865_3_, p_71865_4_ - 1) && func_72169_f(p_71865_1_, p_71865_2_, p_71865_3_ - 1, p_71865_4_ - 1, -1);
                boolean flag3 = func_72169_f(p_71865_1_, p_71865_2_, p_71865_3_, p_71865_4_ + 1, 0) || !p_71865_1_.func_72809_s(p_71865_2_, p_71865_3_, p_71865_4_ + 1) && func_72169_f(p_71865_1_, p_71865_2_, p_71865_3_ - 1, p_71865_4_ + 1, -1);

                if (!p_71865_1_.func_72809_s(p_71865_2_, p_71865_3_ + 1, p_71865_4_))
                {
                    if (p_71865_1_.func_72809_s(p_71865_2_ - 1, p_71865_3_, p_71865_4_) && func_72169_f(p_71865_1_, p_71865_2_ - 1, p_71865_3_ + 1, p_71865_4_, -1))
                    {
                        flag = true;
                    }

                    if (p_71865_1_.func_72809_s(p_71865_2_ + 1, p_71865_3_, p_71865_4_) && func_72169_f(p_71865_1_, p_71865_2_ + 1, p_71865_3_ + 1, p_71865_4_, -1))
                    {
                        flag1 = true;
                    }

                    if (p_71865_1_.func_72809_s(p_71865_2_, p_71865_3_, p_71865_4_ - 1) && func_72169_f(p_71865_1_, p_71865_2_, p_71865_3_ + 1, p_71865_4_ - 1, -1))
                    {
                        flag2 = true;
                    }

                    if (p_71865_1_.func_72809_s(p_71865_2_, p_71865_3_, p_71865_4_ + 1) && func_72169_f(p_71865_1_, p_71865_2_, p_71865_3_ + 1, p_71865_4_ + 1, -1))
                    {
                        flag3 = true;
                    }
                }

                return !flag2 && !flag1 && !flag && !flag3 && p_71865_5_ >= 2 && p_71865_5_ <= 5 ? i1 : (p_71865_5_ == 2 && flag2 && !flag && !flag1 ? i1 : (p_71865_5_ == 3 && flag3 && !flag && !flag1 ? i1 : (p_71865_5_ == 4 && flag && !flag2 && !flag3 ? i1 : (p_71865_5_ == 5 && flag1 && !flag2 && !flag3 ? i1 : 0))));
            }
        }
    }

    public boolean func_71853_i()
    {
        return this.field_72175_a;
    }

    public static boolean func_72173_e(IBlockAccess p_72173_0_, int p_72173_1_, int p_72173_2_, int p_72173_3_, int p_72173_4_)
    {
        int i1 = p_72173_0_.func_72798_a(p_72173_1_, p_72173_2_, p_72173_3_);

        if (i1 == Block.field_72075_av.field_71990_ca)
        {
            return true;
        }
        else if (i1 == 0)
        {
            return false;
        }
        else if (!Block.field_72010_bh.func_94487_f(i1))
        {
            return Block.field_71973_m[i1].func_71853_i() && p_72173_4_ != -1;
        }
        else
        {
            int j1 = p_72173_0_.func_72805_g(p_72173_1_, p_72173_2_, p_72173_3_);
            return p_72173_4_ == (j1 & 3) || p_72173_4_ == Direction.field_71580_e[j1 & 3];
        }
    }

    public static boolean func_72169_f(IBlockAccess p_72169_0_, int p_72169_1_, int p_72169_2_, int p_72169_3_, int p_72169_4_)
    {
        if (func_72173_e(p_72169_0_, p_72169_1_, p_72169_2_, p_72169_3_, p_72169_4_))
        {
            return true;
        }
        else
        {
            int i1 = p_72169_0_.func_72798_a(p_72169_1_, p_72169_2_, p_72169_3_);

            if (i1 == Block.field_72011_bi.field_71990_ca)
            {
                int j1 = p_72169_0_.func_72805_g(p_72169_1_, p_72169_2_, p_72169_3_);
                return p_72169_4_ == (j1 & 3);
            }
            else
            {
                return false;
            }
        }
    }
}
