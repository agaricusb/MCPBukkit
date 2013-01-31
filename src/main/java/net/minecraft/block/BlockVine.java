package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Direction;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.bukkit.craftbukkit.event.CraftEventFactory; // CraftBukkit

public class BlockVine extends Block
{
    public BlockVine(int p_i4019_1_)
    {
        super(p_i4019_1_, 143, Material.field_76255_k);
        this.func_71907_b(true);
        this.func_71849_a(CreativeTabs.field_78031_c);
    }

    public void func_71919_f()
    {
        this.func_71905_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public int func_71857_b()
    {
        return 20;
    }

    public boolean func_71926_d()
    {
        return false;
    }

    public boolean func_71886_c()
    {
        return false;
    }

    public void func_71902_a(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
    {
        int l = p_71902_1_.func_72805_g(p_71902_2_, p_71902_3_, p_71902_4_);
        float f = 1.0F;
        float f1 = 1.0F;
        float f2 = 1.0F;
        float f3 = 0.0F;
        float f4 = 0.0F;
        float f5 = 0.0F;
        boolean flag = l > 0;

        if ((l & 2) != 0)
        {
            f3 = Math.max(f3, 0.0625F);
            f = 0.0F;
            f1 = 0.0F;
            f4 = 1.0F;
            f2 = 0.0F;
            f5 = 1.0F;
            flag = true;
        }

        if ((l & 8) != 0)
        {
            f = Math.min(f, 0.9375F);
            f3 = 1.0F;
            f1 = 0.0F;
            f4 = 1.0F;
            f2 = 0.0F;
            f5 = 1.0F;
            flag = true;
        }

        if ((l & 4) != 0)
        {
            f5 = Math.max(f5, 0.0625F);
            f2 = 0.0F;
            f = 0.0F;
            f3 = 1.0F;
            f1 = 0.0F;
            f4 = 1.0F;
            flag = true;
        }

        if ((l & 1) != 0)
        {
            f2 = Math.min(f2, 0.9375F);
            f5 = 1.0F;
            f = 0.0F;
            f3 = 1.0F;
            f1 = 0.0F;
            f4 = 1.0F;
            flag = true;
        }

        if (!flag && this.func_72151_e(p_71902_1_.func_72798_a(p_71902_2_, p_71902_3_ + 1, p_71902_4_)))
        {
            f1 = Math.min(f1, 0.9375F);
            f4 = 1.0F;
            f = 0.0F;
            f3 = 1.0F;
            f2 = 0.0F;
            f5 = 1.0F;
        }

        this.func_71905_a(f, f1, f2, f3, f4, f5);
    }

    public AxisAlignedBB func_71872_e(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
    {
        return null;
    }

    public boolean func_71850_a_(World p_71850_1_, int p_71850_2_, int p_71850_3_, int p_71850_4_, int p_71850_5_)
    {
        switch (p_71850_5_)
        {
            case 1:
                return this.func_72151_e(p_71850_1_.func_72798_a(p_71850_2_, p_71850_3_ + 1, p_71850_4_));
            case 2:
                return this.func_72151_e(p_71850_1_.func_72798_a(p_71850_2_, p_71850_3_, p_71850_4_ + 1));
            case 3:
                return this.func_72151_e(p_71850_1_.func_72798_a(p_71850_2_, p_71850_3_, p_71850_4_ - 1));
            case 4:
                return this.func_72151_e(p_71850_1_.func_72798_a(p_71850_2_ + 1, p_71850_3_, p_71850_4_));
            case 5:
                return this.func_72151_e(p_71850_1_.func_72798_a(p_71850_2_ - 1, p_71850_3_, p_71850_4_));
            default:
                return false;
        }
    }

    private boolean func_72151_e(int p_72151_1_)
    {
        if (p_72151_1_ == 0)
        {
            return false;
        }
        else
        {
            Block block = Block.field_71973_m[p_72151_1_];
            return block.func_71886_c() && block.field_72018_cp.func_76230_c();
        }
    }

    private boolean func_72150_l(World p_72150_1_, int p_72150_2_, int p_72150_3_, int p_72150_4_)
    {
        int l = p_72150_1_.func_72805_g(p_72150_2_, p_72150_3_, p_72150_4_);
        int i1 = l;

        if (l > 0)
        {
            for (int j1 = 0; j1 <= 3; ++j1)
            {
                int k1 = 1 << j1;

                if ((l & k1) != 0 && !this.func_72151_e(p_72150_1_.func_72798_a(p_72150_2_ + Direction.field_71583_a[j1], p_72150_3_, p_72150_4_ + Direction.field_71581_b[j1])) && (p_72150_1_.func_72798_a(p_72150_2_, p_72150_3_ + 1, p_72150_4_) != this.field_71990_ca || (p_72150_1_.func_72805_g(p_72150_2_, p_72150_3_ + 1, p_72150_4_) & k1) == 0))
                {
                    i1 &= ~k1;
                }
            }
        }

        if (i1 == 0 && !this.func_72151_e(p_72150_1_.func_72798_a(p_72150_2_, p_72150_3_ + 1, p_72150_4_)))
        {
            return false;
        }
        else
        {
            if (i1 != l)
            {
                p_72150_1_.func_72921_c(p_72150_2_, p_72150_3_, p_72150_4_, i1);
            }

            return true;
        }
    }

    public void func_71863_a(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
    {
        if (!p_71863_1_.field_72995_K && !this.func_72150_l(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_))
        {
            this.func_71897_c(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, p_71863_1_.func_72805_g(p_71863_2_, p_71863_3_, p_71863_4_), 0);
            p_71863_1_.func_72859_e(p_71863_2_, p_71863_3_, p_71863_4_, 0);
        }
    }

    public void func_71847_b(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
    {
        if (!p_71847_1_.field_72995_K && p_71847_1_.field_73012_v.nextInt(4) == 0)
        {
            byte b0 = 4;
            int l = 5;
            boolean flag = false;
            int i1;
            int j1;
            int k1;
            label138:

            for (i1 = p_71847_2_ - b0; i1 <= p_71847_2_ + b0; ++i1)
            {
                for (j1 = p_71847_4_ - b0; j1 <= p_71847_4_ + b0; ++j1)
                {
                    for (k1 = p_71847_3_ - 1; k1 <= p_71847_3_ + 1; ++k1)
                    {
                        if (p_71847_1_.func_72798_a(i1, k1, j1) == this.field_71990_ca)
                        {
                            --l;

                            if (l <= 0)
                            {
                                flag = true;
                                break label138;
                            }
                        }
                    }
                }
            }

            i1 = p_71847_1_.func_72805_g(p_71847_2_, p_71847_3_, p_71847_4_);
            j1 = p_71847_1_.field_73012_v.nextInt(6);
            k1 = Direction.field_71579_d[j1];
            int l1;
            int i2;

            if (j1 == 1 && p_71847_3_ < 255 && p_71847_1_.func_72799_c(p_71847_2_, p_71847_3_ + 1, p_71847_4_))
            {
                if (flag)
                {
                    return;
                }

                l1 = p_71847_1_.field_73012_v.nextInt(16) & i1;

                if (l1 > 0)
                {
                    for (i2 = 0; i2 <= 3; ++i2)
                    {
                        if (!this.func_72151_e(p_71847_1_.func_72798_a(p_71847_2_ + Direction.field_71583_a[i2], p_71847_3_ + 1, p_71847_4_ + Direction.field_71581_b[i2])))
                        {
                            l1 &= ~(1 << i2);
                        }
                    }

                    if (l1 > 0)
                    {
                        // CraftBukkit start - fire BlockSpreadEvent
                        org.bukkit.block.Block source = p_71847_1_.getWorld().getBlockAt(p_71847_2_, p_71847_3_, p_71847_4_);
                        org.bukkit.block.Block block = p_71847_1_.getWorld().getBlockAt(p_71847_2_, p_71847_3_ + 1, p_71847_4_);
                        CraftEventFactory.handleBlockSpreadEvent(block, source, this.field_71990_ca, i2);
                        // CraftBukkit end
                    }
                }
            }
            else
            {
                int j2;

                if (j1 >= 2 && j1 <= 5 && (i1 & 1 << k1) == 0)
                {
                    if (flag)
                    {
                        return;
                    }

                    l1 = p_71847_1_.func_72798_a(p_71847_2_ + Direction.field_71583_a[k1], p_71847_3_, p_71847_4_ + Direction.field_71581_b[k1]);

                    if (l1 != 0 && Block.field_71973_m[l1] != null)
                    {
                        if (Block.field_71973_m[l1].field_72018_cp.func_76218_k() && Block.field_71973_m[l1].func_71886_c())
                        {
                            p_71847_1_.func_72921_c(p_71847_2_, p_71847_3_, p_71847_4_, i1 | 1 << k1);
                        }
                    }
                    else
                    {
                        i2 = k1 + 1 & 3;
                        j2 = k1 + 3 & 3;
                        // CraftBukkit start - fire BlockSpreadEvent
                        org.bukkit.block.Block source = p_71847_1_.getWorld().getBlockAt(p_71847_2_, p_71847_3_, p_71847_4_);
                        org.bukkit.block.Block block = p_71847_1_.getWorld().getBlockAt(p_71847_2_ + Direction.field_71583_a[k1], p_71847_3_, p_71847_4_ + Direction.field_71581_b[k1]);

                        if ((i1 & 1 << i2) != 0 && this.func_72151_e(p_71847_1_.func_72798_a(p_71847_2_ + Direction.field_71583_a[k1] + Direction.field_71583_a[i2], p_71847_3_, p_71847_4_ + Direction.field_71581_b[k1] + Direction.field_71581_b[i2])))
                        {
                            CraftEventFactory.handleBlockSpreadEvent(block, source, this.field_71990_ca, 1 << i2);
                        }
                        else if ((i1 & 1 << j2) != 0 && this.func_72151_e(p_71847_1_.func_72798_a(p_71847_2_ + Direction.field_71583_a[k1] + Direction.field_71583_a[j2], p_71847_3_, p_71847_4_ + Direction.field_71581_b[k1] + Direction.field_71581_b[j2])))
                        {
                            CraftEventFactory.handleBlockSpreadEvent(block, source, this.field_71990_ca, 1 << j2);
                        }
                        else if ((i1 & 1 << i2) != 0 && p_71847_1_.func_72799_c(p_71847_2_ + Direction.field_71583_a[k1] + Direction.field_71583_a[i2], p_71847_3_, p_71847_4_ + Direction.field_71581_b[k1] + Direction.field_71581_b[i2]) && this.func_72151_e(p_71847_1_.func_72798_a(p_71847_2_ + Direction.field_71583_a[i2], p_71847_3_, p_71847_4_ + Direction.field_71581_b[i2])))
                        {
                            block = p_71847_1_.getWorld().getBlockAt(p_71847_2_ + Direction.field_71583_a[k1] + Direction.field_71583_a[i2], p_71847_3_, p_71847_4_ + Direction.field_71581_b[k1] + Direction.field_71581_b[i2]);
                            CraftEventFactory.handleBlockSpreadEvent(block, source, this.field_71990_ca, 1 << (k1 + 2 & 3));
                        }
                        else if ((i1 & 1 << j2) != 0 && p_71847_1_.func_72799_c(p_71847_2_ + Direction.field_71583_a[k1] + Direction.field_71583_a[j2], p_71847_3_, p_71847_4_ + Direction.field_71581_b[k1] + Direction.field_71581_b[j2]) && this.func_72151_e(p_71847_1_.func_72798_a(p_71847_2_ + Direction.field_71583_a[j2], p_71847_3_, p_71847_4_ + Direction.field_71581_b[j2])))
                        {
                            block = p_71847_1_.getWorld().getBlockAt(p_71847_2_ + Direction.field_71583_a[k1] + Direction.field_71583_a[j2], p_71847_3_, p_71847_4_ + Direction.field_71581_b[k1] + Direction.field_71581_b[j2]);
                            CraftEventFactory.handleBlockSpreadEvent(block, source, this.field_71990_ca, 1 << (k1 + 2 & 3));
                        }
                        else if (this.func_72151_e(p_71847_1_.func_72798_a(p_71847_2_ + Direction.field_71583_a[k1], p_71847_3_ + 1, p_71847_4_ + Direction.field_71581_b[k1])))
                        {
                            CraftEventFactory.handleBlockSpreadEvent(block, source, this.field_71990_ca, 0);
                        }

                        // CraftBukkit end
                    }
                }
                else if (p_71847_3_ > 1)
                {
                    l1 = p_71847_1_.func_72798_a(p_71847_2_, p_71847_3_ - 1, p_71847_4_);

                    if (l1 == 0)
                    {
                        i2 = p_71847_1_.field_73012_v.nextInt(16) & i1;

                        if (i2 > 0)
                        {
                            // CraftBukkit start - fire BlockSpreadEvent
                            org.bukkit.block.Block source = p_71847_1_.getWorld().getBlockAt(p_71847_2_, p_71847_3_, p_71847_4_);
                            org.bukkit.block.Block block = p_71847_1_.getWorld().getBlockAt(p_71847_2_, p_71847_3_ - 1, p_71847_4_);
                            CraftEventFactory.handleBlockSpreadEvent(block, source, this.field_71990_ca, i2);
                            // CraftBukkit end
                        }
                    }
                    else if (l1 == this.field_71990_ca)
                    {
                        i2 = p_71847_1_.field_73012_v.nextInt(16) & i1;
                        j2 = p_71847_1_.func_72805_g(p_71847_2_, p_71847_3_ - 1, p_71847_4_);

                        if (j2 != (j2 | i2))
                        {
                            p_71847_1_.func_72921_c(p_71847_2_, p_71847_3_ - 1, p_71847_4_, j2 | i2);
                        }
                    }
                }
            }
        }
    }

    public int func_85104_a(World p_85104_1_, int p_85104_2_, int p_85104_3_, int p_85104_4_, int p_85104_5_, float p_85104_6_, float p_85104_7_, float p_85104_8_, int p_85104_9_)
    {
        byte b0 = 0;

        switch (p_85104_5_)
        {
            case 2:
                b0 = 1;
                break;
            case 3:
                b0 = 4;
                break;
            case 4:
                b0 = 8;
                break;
            case 5:
                b0 = 2;
        }

        return b0 != 0 ? b0 : p_85104_9_;
    }

    public int func_71885_a(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
    {
        return 0;
    }

    public int func_71925_a(Random p_71925_1_)
    {
        return 0;
    }

    public void func_71893_a(World p_71893_1_, EntityPlayer p_71893_2_, int p_71893_3_, int p_71893_4_, int p_71893_5_, int p_71893_6_)
    {
        if (!p_71893_1_.field_72995_K && p_71893_2_.func_71045_bC() != null && p_71893_2_.func_71045_bC().field_77993_c == Item.field_77745_be.field_77779_bT)
        {
            p_71893_2_.func_71064_a(StatList.field_75934_C[this.field_71990_ca], 1);
            this.func_71929_a(p_71893_1_, p_71893_3_, p_71893_4_, p_71893_5_, new ItemStack(Block.field_71998_bu, 1, 0));
        }
        else
        {
            super.func_71893_a(p_71893_1_, p_71893_2_, p_71893_3_, p_71893_4_, p_71893_5_, p_71893_6_);
        }
    }
}
