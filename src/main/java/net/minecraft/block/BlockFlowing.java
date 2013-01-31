package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

// CraftBukkit start
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockFromToEvent;
// CraftBukkit end

public class BlockFlowing extends BlockFluid
{
    int field_72214_a = 0;
    boolean[] field_72212_b = new boolean[4];
    int[] field_72213_c = new int[4];

    protected BlockFlowing(int p_i3965_1_, Material p_i3965_2_)
    {
        super(p_i3965_1_, p_i3965_2_);
    }

    private void func_72205_l(World p_72205_1_, int p_72205_2_, int p_72205_3_, int p_72205_4_)
    {
        int l = p_72205_1_.func_72805_g(p_72205_2_, p_72205_3_, p_72205_4_);
        p_72205_1_.func_72961_c(p_72205_2_, p_72205_3_, p_72205_4_, this.field_71990_ca + 1, l);
        p_72205_1_.func_72909_d(p_72205_2_, p_72205_3_, p_72205_4_, p_72205_2_, p_72205_3_, p_72205_4_);
    }

    public boolean func_71918_c(IBlockAccess p_71918_1_, int p_71918_2_, int p_71918_3_, int p_71918_4_)
    {
        return this.field_72018_cp != Material.field_76256_h;
    }

    public void func_71847_b(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
    {
        // CraftBukkit start
        org.bukkit.World bworld = p_71847_1_.getWorld();
        org.bukkit.Server server = p_71847_1_.getServer();
        org.bukkit.block.Block source = bworld == null ? null : bworld.getBlockAt(p_71847_2_, p_71847_3_, p_71847_4_);
        // CraftBukkit end
        int l = this.func_72198_f_(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_);
        byte b0 = 1;

        if (this.field_72018_cp == Material.field_76256_h && !p_71847_1_.field_73011_w.field_76575_d)
        {
            b0 = 2;
        }

        boolean flag = true;
        int i1;

        if (l > 0)
        {
            byte b1 = -100;
            this.field_72214_a = 0;
            int j1 = this.func_72211_e(p_71847_1_, p_71847_2_ - 1, p_71847_3_, p_71847_4_, b1);
            j1 = this.func_72211_e(p_71847_1_, p_71847_2_ + 1, p_71847_3_, p_71847_4_, j1);
            j1 = this.func_72211_e(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_ - 1, j1);
            j1 = this.func_72211_e(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_ + 1, j1);
            i1 = j1 + b0;

            if (i1 >= 8 || j1 < 0)
            {
                i1 = -1;
            }

            if (this.func_72198_f_(p_71847_1_, p_71847_2_, p_71847_3_ + 1, p_71847_4_) >= 0)
            {
                int k1 = this.func_72198_f_(p_71847_1_, p_71847_2_, p_71847_3_ + 1, p_71847_4_);

                if (k1 >= 8)
                {
                    i1 = k1;
                }
                else
                {
                    i1 = k1 + 8;
                }
            }

            if (this.field_72214_a >= 2 && this.field_72018_cp == Material.field_76244_g)
            {
                if (p_71847_1_.func_72803_f(p_71847_2_, p_71847_3_ - 1, p_71847_4_).func_76220_a())
                {
                    i1 = 0;
                }
                else if (p_71847_1_.func_72803_f(p_71847_2_, p_71847_3_ - 1, p_71847_4_) == this.field_72018_cp && p_71847_1_.func_72805_g(p_71847_2_, p_71847_3_, p_71847_4_) == 0)
                {
                    i1 = 0;
                }
            }

            if (this.field_72018_cp == Material.field_76256_h && l < 8 && i1 < 8 && i1 > l && p_71847_5_.nextInt(4) != 0)
            {
                i1 = l;
                flag = false;
            }

            if (i1 == l)
            {
                if (flag)
                {
                    this.func_72205_l(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_);
                }
            }
            else
            {
                l = i1;

                if (i1 < 0)
                {
                    p_71847_1_.func_72859_e(p_71847_2_, p_71847_3_, p_71847_4_, 0);
                }
                else
                {
                    p_71847_1_.func_72921_c(p_71847_2_, p_71847_3_, p_71847_4_, i1);
                    p_71847_1_.func_72836_a(p_71847_2_, p_71847_3_, p_71847_4_, this.field_71990_ca, this.func_71859_p_());
                    p_71847_1_.func_72898_h(p_71847_2_, p_71847_3_, p_71847_4_, this.field_71990_ca);
                }
            }
        }
        else
        {
            this.func_72205_l(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_);
        }

        if (this.func_72207_p(p_71847_1_, p_71847_2_, p_71847_3_ - 1, p_71847_4_))
        {
            // CraftBukkit start - send "down" to the server
            BlockFromToEvent event = new BlockFromToEvent(source, BlockFace.DOWN);

            if (server != null)
            {
                server.getPluginManager().callEvent(event);
            }

            if (!event.isCancelled())
            {
                if (this.field_72018_cp == Material.field_76256_h && p_71847_1_.func_72803_f(p_71847_2_, p_71847_3_ - 1, p_71847_4_) == Material.field_76244_g)
                {
                    p_71847_1_.func_72859_e(p_71847_2_, p_71847_3_ - 1, p_71847_4_, Block.field_71981_t.field_71990_ca);
                    this.func_72201_j(p_71847_1_, p_71847_2_, p_71847_3_ - 1, p_71847_4_);
                    return;
                }

                if (l >= 8)
                {
                    this.func_72210_i(p_71847_1_, p_71847_2_, p_71847_3_ - 1, p_71847_4_, l);
                }
                else
                {
                    this.func_72210_i(p_71847_1_, p_71847_2_, p_71847_3_ - 1, p_71847_4_, l + 8);
                }
            }

            // CraftBukkit end
        }
        else if (l >= 0 && (l == 0 || this.func_72208_o(p_71847_1_, p_71847_2_, p_71847_3_ - 1, p_71847_4_)))
        {
            boolean[] aboolean = this.func_72206_n(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_);
            i1 = l + b0;

            if (l >= 8)
            {
                i1 = 1;
            }

            if (i1 >= 8)
            {
                return;
            }

            // CraftBukkit start - all four cardinal directions. Do not change the order!
            BlockFace[] faces = new BlockFace[] { BlockFace.WEST, BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH };
            int index = 0;

            for (BlockFace currentFace : faces)
            {
                if (aboolean[index])
                {
                    BlockFromToEvent event = new BlockFromToEvent(source, currentFace);

                    if (server != null)
                    {
                        server.getPluginManager().callEvent(event);
                    }

                    if (!event.isCancelled())
                    {
                        this.func_72210_i(p_71847_1_, p_71847_2_ + currentFace.getModX(), p_71847_3_, p_71847_4_ + currentFace.getModZ(), i1);
                    }
                }

                index++;
            }

            // CraftBukkit end
        }
    }

    private void func_72210_i(World p_72210_1_, int p_72210_2_, int p_72210_3_, int p_72210_4_, int p_72210_5_)
    {
        if (this.func_72207_p(p_72210_1_, p_72210_2_, p_72210_3_, p_72210_4_))
        {
            int i1 = p_72210_1_.func_72798_a(p_72210_2_, p_72210_3_, p_72210_4_);

            if (i1 > 0)
            {
                if (this.field_72018_cp == Material.field_76256_h)
                {
                    this.func_72201_j(p_72210_1_, p_72210_2_, p_72210_3_, p_72210_4_);
                }
                else
                {
                    Block.field_71973_m[i1].func_71897_c(p_72210_1_, p_72210_2_, p_72210_3_, p_72210_4_, p_72210_1_.func_72805_g(p_72210_2_, p_72210_3_, p_72210_4_), 0);
                }
            }

            p_72210_1_.func_72832_d(p_72210_2_, p_72210_3_, p_72210_4_, this.field_71990_ca, p_72210_5_);
        }
    }

    private int func_72209_d(World p_72209_1_, int p_72209_2_, int p_72209_3_, int p_72209_4_, int p_72209_5_, int p_72209_6_)
    {
        int j1 = 1000;

        for (int k1 = 0; k1 < 4; ++k1)
        {
            if ((k1 != 0 || p_72209_6_ != 1) && (k1 != 1 || p_72209_6_ != 0) && (k1 != 2 || p_72209_6_ != 3) && (k1 != 3 || p_72209_6_ != 2))
            {
                int l1 = p_72209_2_;
                int i2 = p_72209_4_;

                if (k1 == 0)
                {
                    l1 = p_72209_2_ - 1;
                }

                if (k1 == 1)
                {
                    ++l1;
                }

                if (k1 == 2)
                {
                    i2 = p_72209_4_ - 1;
                }

                if (k1 == 3)
                {
                    ++i2;
                }

                if (!this.func_72208_o(p_72209_1_, l1, p_72209_3_, i2) && (p_72209_1_.func_72803_f(l1, p_72209_3_, i2) != this.field_72018_cp || p_72209_1_.func_72805_g(l1, p_72209_3_, i2) != 0))
                {
                    if (!this.func_72208_o(p_72209_1_, l1, p_72209_3_ - 1, i2))
                    {
                        return p_72209_5_;
                    }

                    if (p_72209_5_ < 4)
                    {
                        int j2 = this.func_72209_d(p_72209_1_, l1, p_72209_3_, i2, p_72209_5_ + 1, k1);

                        if (j2 < j1)
                        {
                            j1 = j2;
                        }
                    }
                }
            }
        }

        return j1;
    }

    private boolean[] func_72206_n(World p_72206_1_, int p_72206_2_, int p_72206_3_, int p_72206_4_)
    {
        int l;
        int i1;

        for (l = 0; l < 4; ++l)
        {
            this.field_72213_c[l] = 1000;
            i1 = p_72206_2_;
            int j1 = p_72206_4_;

            if (l == 0)
            {
                i1 = p_72206_2_ - 1;
            }

            if (l == 1)
            {
                ++i1;
            }

            if (l == 2)
            {
                j1 = p_72206_4_ - 1;
            }

            if (l == 3)
            {
                ++j1;
            }

            if (!this.func_72208_o(p_72206_1_, i1, p_72206_3_, j1) && (p_72206_1_.func_72803_f(i1, p_72206_3_, j1) != this.field_72018_cp || p_72206_1_.func_72805_g(i1, p_72206_3_, j1) != 0))
            {
                if (this.func_72208_o(p_72206_1_, i1, p_72206_3_ - 1, j1))
                {
                    this.field_72213_c[l] = this.func_72209_d(p_72206_1_, i1, p_72206_3_, j1, 1, l);
                }
                else
                {
                    this.field_72213_c[l] = 0;
                }
            }
        }

        l = this.field_72213_c[0];

        for (i1 = 1; i1 < 4; ++i1)
        {
            if (this.field_72213_c[i1] < l)
            {
                l = this.field_72213_c[i1];
            }
        }

        for (i1 = 0; i1 < 4; ++i1)
        {
            this.field_72212_b[i1] = this.field_72213_c[i1] == l;
        }

        return this.field_72212_b;
    }

    private boolean func_72208_o(World p_72208_1_, int p_72208_2_, int p_72208_3_, int p_72208_4_)
    {
        int l = p_72208_1_.func_72798_a(p_72208_2_, p_72208_3_, p_72208_4_);

        if (l != Block.field_72054_aE.field_71990_ca && l != Block.field_72045_aL.field_71990_ca && l != Block.field_72053_aD.field_71990_ca && l != Block.field_72055_aF.field_71990_ca && l != Block.field_72040_aX.field_71990_ca)
        {
            if (l == 0)
            {
                return false;
            }
            else
            {
                Material material = Block.field_71973_m[l].field_72018_cp;
                return material == Material.field_76237_B ? true : material.func_76230_c();
            }
        }
        else
        {
            return true;
        }
    }

    protected int func_72211_e(World p_72211_1_, int p_72211_2_, int p_72211_3_, int p_72211_4_, int p_72211_5_)
    {
        int i1 = this.func_72198_f_(p_72211_1_, p_72211_2_, p_72211_3_, p_72211_4_);

        if (i1 < 0)
        {
            return p_72211_5_;
        }
        else
        {
            if (i1 == 0)
            {
                ++this.field_72214_a;
            }

            if (i1 >= 8)
            {
                i1 = 0;
            }

            return p_72211_5_ >= 0 && i1 >= p_72211_5_ ? p_72211_5_ : i1;
        }
    }

    private boolean func_72207_p(World p_72207_1_, int p_72207_2_, int p_72207_3_, int p_72207_4_)
    {
        Material material = p_72207_1_.func_72803_f(p_72207_2_, p_72207_3_, p_72207_4_);
        return material == this.field_72018_cp ? false : (material == Material.field_76256_h ? false : !this.func_72208_o(p_72207_1_, p_72207_2_, p_72207_3_, p_72207_4_));
    }

    public void func_71861_g(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
    {
        super.func_71861_g(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_);

        if (p_71861_1_.func_72798_a(p_71861_2_, p_71861_3_, p_71861_4_) == this.field_71990_ca)
        {
            p_71861_1_.func_72836_a(p_71861_2_, p_71861_3_, p_71861_4_, this.field_71990_ca, this.func_71859_p_());
        }
    }

    public boolean func_82506_l()
    {
        return false;
    }
}
